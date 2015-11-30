package pl.gda.pg.ds.sok.services.impl;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import pl.gda.pg.ds.sok.beans.RatingBean;
import pl.gda.pg.ds.sok.entities.AnswerHistory;
import pl.gda.pg.ds.sok.entities.Candidate;
import pl.gda.pg.ds.sok.entities.Rating;
import pl.gda.pg.ds.sok.services.RatingService;
import pl.gda.pg.ds.sok.utils.NetworkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rating")
public class RatingServiceImpl extends AbstractService implements RatingService {
	
	private static final Logger logger = Logger.getLogger(RatingServiceImpl.class);
	private static final int MIN_RATING = 1;
	private static final int MAX_RATING = 5;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setRating(@Context HttpServletRequest request, RatingBean rating) {
		try {
			session.beginTransaction();

			if (!canAdmin(rating.getAuthToken())) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
			if (rating.getRating() < MIN_RATING || rating.getRating() > MAX_RATING) {
				throw new ConstraintViolationException("Rating lower than min or higher than max", null, null);
			}

			List<AnswerHistory> resultList = getLastAnswer(rating.getTaskId(), rating.getToken());
			if (resultList.size() == 0) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			AnswerHistory answer = resultList.get(0);

			Query query = session.createQuery("from Candidate where token = :token");
			query.setString("token", rating.getAuthToken());
			List<Candidate> candidateList = query.list();
			Candidate assessor = candidateList.get(0);

			boolean update = false;
			query = session.createQuery("from Rating where answer.id = :answer order by date desc");
			query.setLong("answer", answer.getId());
			query.setMaxResults(1);
			List<Rating> ratingList = query.list();
			if (ratingList.size() > 0) {
				Rating ratingToUpdate = ratingList.get(0);
				if (ratingToUpdate.getComment().equals(rating.getComment()) && ratingToUpdate.getRating() == rating.getRating()) {
					throw new ConstraintViolationException("Rating not modified", null, null);
				}
				update = true;
			}
			session.save(new Rating(rating.getRating(), rating.getComment(), assessor, answer, NetworkUtil.getIpAddress(request)));
			session.getTransaction().commit();

			return Response.status(update ? Response.Status.ACCEPTED : Response.Status.CREATED).build();
		} catch (ConstraintViolationException e) {
			logger.error(e);
			session.getTransaction().rollback();
			return Response.status(Response.Status.CONFLICT).build();
		} catch (Exception e) {
			logger.error(e);
			session.getTransaction().rollback();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} finally {
			session.close();
		}
	}

	@GET
	@Path("/{taskId}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRating(@PathParam("taskId") String taskId, @PathParam("token") String token) {
		List<AnswerHistory> resultList = getLastAnswer(taskId, token);
		if (resultList.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		AnswerHistory answer = resultList.get(0);

		List<Rating> ratingList = getRatings(taskId, true);
		if (ratingList.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		boolean isFresh = ratingList.get(0).getAnswerId().equals(answer.getId());
		RatingBean rating = new RatingBean(ratingList.get(0).getRating(), ratingList.get(0).getComment(), null, isFresh);
		return Response.ok(rating).build();
	}

	@GET
	@Path("/{taskId}/{token}/{authToken}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRatingForAdmin(@PathParam("taskId") String taskId, @PathParam("token") String token, @PathParam("authToken") String authToken) {
		if (!canAdmin(authToken)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}

		List<AnswerHistory> resultList = getLastAnswer(taskId, token);
		if (resultList.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		AnswerHistory answer = resultList.get(0);

		List<Rating> ratingList = getRatings(taskId, false);
		if (ratingList.size() == 0) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		List<RatingBean> ratings = Lists.newArrayList();
		for (Rating rating : ratingList) {
			boolean isFresh = ratingList.get(0).getAnswerId().equals(answer.getId());
			ratings.add(new RatingBean(ratingList.get(0).getRating(), ratingList.get(0).getComment(), ratingList.get(0).getAssessorName(), isFresh));
		}

		return Response.ok(ratings).build();
	}

	private List<AnswerHistory> getLastAnswer(String taskId, String token) {
		Query query = session.createQuery("from AnswerHistory a where a.task.id = :taskId and a.candidate.token = :token order by answerDate desc");
		query.setLong("taskId", Long.parseLong(taskId));
		query.setString("token", token);
		query.setMaxResults(1);

		return query.list();
	}

	private List<Rating> getRatings(String taskId, boolean lastOnly) {
		Query query = session.createQuery("from Rating where answer.task.id = :task order by date desc");
		query.setLong("task", Long.parseLong(taskId));
		if (lastOnly) {
			query.setMaxResults(1);
		}

		return query.list();
	}
}
