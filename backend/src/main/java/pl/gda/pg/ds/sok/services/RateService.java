package pl.gda.pg.ds.sok.services;

import pl.gda.pg.ds.sok.beans.CandidateBean;
import pl.gda.pg.ds.sok.beans.RateBean;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

public interface RateService {

	Response setRate(HttpServletRequest request, RateBean rate);
}
