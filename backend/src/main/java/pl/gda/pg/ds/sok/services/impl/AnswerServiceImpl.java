package pl.gda.pg.ds.sok.services.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import pl.gda.pg.ds.sok.beans.AnswerBean;
import pl.gda.pg.ds.sok.beans.SimpleResponseBean;
import pl.gda.pg.ds.sok.entities.Answer;
import pl.gda.pg.ds.sok.entities.Candidate;
import pl.gda.pg.ds.sok.entities.Task;
import pl.gda.pg.ds.sok.services.AnswerService;
import pl.gda.pg.ds.sok.utils.DbUtil;
import pl.gda.pg.ds.sok.utils.NetworkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/answer")
public class AnswerServiceImpl implements AnswerService {

    private static final Logger logger = Logger.getLogger(AnswerServiceImpl.class);

    @GET
    @Path("/{taskId}/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response getAnswerByTaskAndToken(@PathParam("taskId") String taskId, @PathParam("token") String token) {
        try {
            Session session = DbUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Answer a where a.task.id = :taskId and a.candidate.token = :token");
            query.setLong("taskId", Long.parseLong(taskId));
            query.setString("token", token);

            List<Answer> resultList = query.list();
            if (resultList.size() > 0) {
                SimpleResponseBean arb = new SimpleResponseBean();
                arb.setContent(resultList.get(0).getContent());
                return Response.ok(arb).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public Response updateAnswer(@Context HttpServletRequest request, AnswerBean answer) {
        try {
            Session session = DbUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from Answer where candidate.id = :candidateId and task.id = :taskId");
            query.setLong("candidateId", answer.getCandidateId());
            query.setLong("taskId", answer.getTaskId());

            boolean update = false;
            List<Answer> resultList = query.list();
            if (resultList.size() > 0) {
                Answer answerToUpdate = resultList.get(0);
                answerToUpdate.setContent(answer.getContent());
                session.save(answerToUpdate);
                update = true;
            } else {
                session.save(new Answer(answer.getContent(), new Candidate(answer.getCandidateId()), new Task(answer.getTaskId()),NetworkUtil.getIpAddress(request)));
            }
            session.getTransaction().commit();

            return Response.status(update ? Response.Status.ACCEPTED : Response.Status.CREATED).build();
        } catch (ConstraintViolationException e) {
            logger.error(e);
            return Response.status(Response.Status.CONFLICT).build();
        } catch (Exception e) {
            logger.error(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
