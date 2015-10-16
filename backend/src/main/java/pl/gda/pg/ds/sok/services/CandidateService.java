package pl.gda.pg.ds.sok.services;

import javax.ws.rs.core.Response;

import pl.gda.pg.ds.sok.beans.CandidateBean;

public interface CandidateService {

	Response getCandidateByToken(String token);
	Response createCandidate(CandidateBean candidate);
}
