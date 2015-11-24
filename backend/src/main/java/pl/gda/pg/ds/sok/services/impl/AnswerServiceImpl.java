package pl.gda.pg.ds.sok.services.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import pl.gda.pg.ds.sok.beans.AnswerBean;
import pl.gda.pg.ds.sok.entities.Answer;
import pl.gda.pg.ds.sok.entities.AnswerHistory;
import pl.gda.pg.ds.sok.entities.Candidate;
import pl.gda.pg.ds.sok.entities.Task;
import pl.gda.pg.ds.sok.services.AnswerService;
import pl.gda.pg.ds.sok.utils.DbUtil;
import pl.gda.pg.ds.sok.utils.NetworkUtil;
import pl.gda.pg.ds.sok.utils.PropertiesUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/answer")
public class AnswerServiceImpl extends AbstractService implements AnswerService {

    private static final Logger logger = Logger.getLogger(AnswerServiceImpl.class);

    @GET
    @Path("/{taskId}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response getAnswerByTaskAndToken(@PathParam("taskId") String taskId, @PathParam("token") String token) {
        try {
            Query query = session.createQuery("from Answer a where a.task.id = :taskId and a.candidate.token = :token");
            query.setLong("taskId", Long.parseLong(taskId));
            query.setString("token", token);

            List<Answer> resultList = query.list();
            if (resultList.size() > 0) {
                return Response.ok(resultList.get(0)).build();
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

    @GET
    @Path("/{taskId}/{token}/{authToken}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response getAnswerByTaskAndTokenForAdmin(@PathParam("taskId") String taskId, @PathParam("token") String token, @PathParam("authToken") String authToken) {
        try {
            if (!canAdmin(authToken)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }

            Query query = session.createQuery("from AnswerHistory a where a.task.id = :taskId and a.candidate.token = :token order by answerDate desc");
            query.setLong("taskId", Long.parseLong(taskId));
            query.setString("token", token);

            List<AnswerHistory> resultList = query.list();
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response updateAnswer(@Context HttpServletRequest request, AnswerBean answer) throws ConstraintViolationException {
        try {
            session.beginTransaction();
            Query query = session.createQuery("from Candidate where token = :token");
            query.setString("token", answer.getToken());

            List<Candidate> candidateList = query.list();
            if (candidateList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            Candidate candidate = candidateList.get(0);

            query = session.createQuery("from Answer where candidate.token = :token and task.id = :taskId");
            query.setString("token", answer.getToken());
            query.setLong("taskId", answer.getTaskId());

            boolean update = false;
            List<Answer> resultList = query.list();
            if (resultList.size() > 0) {
                Answer answerToUpdate = resultList.get(0);
                if (answerToUpdate.getContent().equals(answer.getContent())) {
                    throw new ConstraintViolationException("Answer not modified", null, null);
                }
                answerToUpdate.setContent(answer.getContent());
                session.save(answerToUpdate);
                update = true;
            } else {
                session.save(new Answer(answer.getContent(), candidate, new Task(answer.getTaskId()),NetworkUtil.getIpAddress(request)));
            }
            session.save(new AnswerHistory(answer.getContent(), candidate, new Task(answer.getTaskId()),NetworkUtil.getIpAddress(request)));
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
}
