package pl.gda.pg.ds.sok.services;

import javax.ws.rs.core.Response;

public interface CandidatesService {
	Response getCandidates(String token);
}
