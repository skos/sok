package pl.gda.pg.ds.sok.services.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import pl.gda.pg.ds.sok.beans.CandidateBean;
import pl.gda.pg.ds.sok.entities.Candidate;
import pl.gda.pg.ds.sok.services.CandidateService;
import pl.gda.pg.ds.sok.utils.DbUtil;
import pl.gda.pg.ds.sok.utils.MsgUtil;
import pl.gda.pg.ds.sok.utils.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
public class CandidateServiceImpl implements CandidateService {
	
	private static final Logger logger = Logger.getLogger(CandidateServiceImpl.class);

	@GET
	@Path("/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")
	public Response getCandidateByToken(@PathParam("token") String token) {
		try {
			Session session = DbUtil.getSessionFactory().openSession();
			Query query = session.createQuery("from Candidate where token = :token");
			query.setString("token", token);
			
			List<Candidate> resultList = query.list();
			if (resultList.size() > 0) {
				return Response.ok(resultList.get(0)).build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			logger.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCandidate(@Context HttpServletRequest request, CandidateBean candidate) {
		try {
			Session session = DbUtil.getSessionFactory().openSession();
			session.beginTransaction();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
			session.save(new Candidate(candidate.getName(), candidate.getEmail(), candidate.getToken(), ipAddress));
			session.getTransaction().commit();

			String mailBody = PropertiesUtil.getProperty("mail.body");
			mailBody = mailBody.replace(MsgUtil.TOKEN_PLACEHOLDER, candidate.getToken());
			MsgUtil.sendMail(candidate.getEmail(), candidate.getName(), PropertiesUtil.getProperty("mail.subject"), mailBody);

			return Response.status(Response.Status.CREATED).build();
		} catch (ConstraintViolationException e) {
			logger.error(e);
			return Response.status(Response.Status.CONFLICT).build();
		} catch (Exception e) {
			logger.error(e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}
