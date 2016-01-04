package pl.gda.pg.ds.sok.services.impl;

import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.exception.ConstraintViolationException;
import pl.gda.pg.ds.sok.beans.TaskBean;
import pl.gda.pg.ds.sok.entities.Task;
import pl.gda.pg.ds.sok.services.TasksService;
import pl.gda.pg.ds.sok.utils.MsgUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tasks")
@Api(description = "List and create tasks")
public class TasksServiceImpl extends AbstractService implements TasksService {
	
	private static final Logger logger = Logger.getLogger(TasksServiceImpl.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@SuppressWarnings("unchecked")
	public Response getTasks() {
		try {
			Query query = session.createQuery("from Task");

			List<Task> resultList = query.list();
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
	@Path("/{authToken}/newTask")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTask(@Context HttpServletRequest request, @PathParam("authToken") String authToken, TaskBean task) {
		try {
			if (!canAdmin(authToken)) {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
			session.beginTransaction();
			session.save(new Task(task.getTitle(), task.getType(), task.getContent(), task.getDifficulty()));
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
