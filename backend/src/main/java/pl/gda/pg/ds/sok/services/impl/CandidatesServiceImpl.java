package pl.gda.pg.ds.sok.services.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import pl.gda.pg.ds.sok.entities.Candidate;
import pl.gda.pg.ds.sok.entities.Task;
import pl.gda.pg.ds.sok.services.CandidatesService;
import pl.gda.pg.ds.sok.services.TasksService;
import pl.gda.pg.ds.sok.utils.DbUtil;
import pl.gda.pg.ds.sok.utils.PropertiesUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/candidates")
public class CandidatesServiceImpl implements CandidatesService {
	
	private static final Logger logger = Logger.getLogger(CandidatesServiceImpl.class);

	@GET
	@Path("/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")
	public Response getCandidates(@PathParam("token") String token) {
		Session session = DbUtil.getSession();

		try {
			Query query = session.createQuery("from Candidate where token = :token");
			query.setString("token", token);

			List<Candidate> candidateList = query.list();
			if (candidateList.isEmpty()) {
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			Candidate candidate = candidateList.get(0);

			if (!candidate.getEmail().endsWith(PropertiesUtil.getProperty("admin.domain"))) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}

			query = session.createQuery("from Candidate");

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
}
