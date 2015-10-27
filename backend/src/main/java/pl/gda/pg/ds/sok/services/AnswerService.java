package pl.gda.pg.ds.sok.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import pl.gda.pg.ds.sok.beans.AnswerBean;

public interface AnswerService {

	Response getAnswerByTaskAndToken(String taskId, String token);
	Response updateAnswer(HttpServletRequest request, AnswerBean answer);
}
