package pl.gda.pg.ds.sok.services;

import pl.gda.pg.ds.sok.beans.MailBean;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public interface CandidatesService {
	Response getCandidatesForAdmin(String authToken);
	Response sendEmailToCandidatesByAnswersCount(@Context HttpServletRequest request, MailBean mail);
}
