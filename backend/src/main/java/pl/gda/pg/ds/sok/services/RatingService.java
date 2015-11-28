package pl.gda.pg.ds.sok.services;

import pl.gda.pg.ds.sok.beans.RatingBean;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

public interface RatingService {

	Response setRating(HttpServletRequest request, RatingBean rate);
	//Response getRating(String taskId, String token);
	//Response getRatingForAdmin(String taskId, String token, String authToken);

}
