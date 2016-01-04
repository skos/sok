package pl.gda.pg.ds.sok.services.impl;

import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import pl.gda.pg.ds.sok.beans.MailBean;
import pl.gda.pg.ds.sok.entities.Candidate;
import pl.gda.pg.ds.sok.services.CandidatesService;
import pl.gda.pg.ds.sok.utils.MsgUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.NumberFormat;
import java.util.List;

@Path("/candidates")
@Api(description = "List candidates")
public class CandidatesServiceImpl extends AbstractService implements CandidatesService {
	
	private static final Logger logger = Logger.getLogger(CandidatesServiceImpl.class);

	@GET
	@Path("/{authToken}")
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")
	public Response getCandidatesForAdmin(@PathParam("authToken") String authToken) {
		try {
			if (!canAdmin(authToken)) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}

			Query query = session.createQuery("from Candidate");

			List<Candidate> resultList = query.list();
			if (resultList.size() > 0) {
				return Response.ok(resultList).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			logger.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} finally {
			session.close();
		}
	}

	@PUT
	@Path("/{authToken}/mail")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendEmailToCandidatesByAnswersCount(@Context HttpServletRequest request, @PathParam("authToken") String authToken, MailBean mail) {
		try {
			if (!canAdmin(authToken)) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}

			Integer minAnswers;
			Integer maxAnswers;

			try {
				minAnswers = Integer.parseInt(mail.getMinAnswers());
				maxAnswers = Integer.parseInt(mail.getMaxAnswers());
			} catch(Exception e) {
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}

			if(minAnswers < -1 || maxAnswers < -1) {
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}

			Query query;
			StringBuilder queryString = new StringBuilder("from Candidate where ");

			if(minAnswers == -1) {
				queryString.append("answers.size <= :maxAnswers ");
			} else if(maxAnswers == -1) {
				queryString.append("answers.size >= :minAnswers ");
			} else {
				queryString.append("answers.size <= :maxAnswers and answers.size >= :minAnswers ");
			}
			query = session.createQuery(queryString.toString());

			if(minAnswers == -1) {
				query.setInteger("maxAnswers", maxAnswers);
			} else if(maxAnswers == -1) {
				query.setInteger("minAnswers", minAnswers);
			} else {
				query.setInteger("maxAnswers", maxAnswers);
				query.setInteger("minAnswers", minAnswers);
			}

			List<Candidate> resultList = query.list();
			if (resultList.size() > 0) {
				for(Candidate candidate : resultList) {
					MsgUtil.sendMail(candidate.getEmail(), candidate.getName(), mail.getSubject(), mail.getContent());
				}
				return Response.status(Response.Status.OK).build();
			} else {
				return Response.status(Response.Status.NO_CONTENT).build();
			}
		} catch (Exception e) {
			logger.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		} finally {
			session.close();
		}
	}
}
