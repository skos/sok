package pl.gda.pg.ds.sok.services.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import pl.gda.pg.ds.sok.beans.CandidateBean;
import pl.gda.pg.ds.sok.beans.MailBean;
import pl.gda.pg.ds.sok.entities.Candidate;
import pl.gda.pg.ds.sok.entities.Task;
import pl.gda.pg.ds.sok.services.CandidatesService;
import pl.gda.pg.ds.sok.services.TasksService;
import pl.gda.pg.ds.sok.utils.DbUtil;
import pl.gda.pg.ds.sok.utils.MsgUtil;
import pl.gda.pg.ds.sok.utils.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/candidates")
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
	@Path("/mail")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendEmailToCandidatesByAnswersCount(@Context HttpServletRequest request, MailBean mail) {
		try {
			Query query = session.createQuery("from Candidate where answers.size > :answersCount");
			query.setInteger("answersCount", Integer.parseInt(mail.getAnswers()));
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
