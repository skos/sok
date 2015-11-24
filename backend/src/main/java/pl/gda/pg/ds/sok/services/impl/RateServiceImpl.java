package pl.gda.pg.ds.sok.services.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import pl.gda.pg.ds.sok.beans.RateBean;
import pl.gda.pg.ds.sok.entities.AnswerHistory;
import pl.gda.pg.ds.sok.entities.Rate;
import pl.gda.pg.ds.sok.services.RateService;
import pl.gda.pg.ds.sok.utils.NetworkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rate")
public class RateServiceImpl extends AbstractService implements RateService {
	
	private static final Logger logger = Logger.getLogger(RateServiceImpl.class);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setRate(@Context HttpServletRequest request, RateBean rate) {
		try {
			session.beginTransaction();

			if (!canAdmin(rate.getAuthToken())) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}

			Query query = session.createQuery("from AnswerHistory a where a.task.id = :taskId and a.candidate.token = :token order by answerDate desc");
			query.setLong("taskId", Long.parseLong(rate.getTaskId()));
			query.setString("token", rate.getToken());
			query.setMaxResults(1);

			List<AnswerHistory> resultList = query.list();
			if (resultList.size() == 0) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}

			session.save(new Rate(rate.getRate(), rate.getComment(), resultList.get(0), NetworkUtil.getIpAddress(request)));
			session.getTransaction().commit();

			return Response.status(Response.Status.CREATED).build();
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
}
