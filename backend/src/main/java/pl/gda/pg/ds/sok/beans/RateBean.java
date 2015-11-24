package pl.gda.pg.ds.sok.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RateBean {

	private int rate;
	private String comment;
	private String token;
	private String taskId;
	private  String authToken;

	public int getRate() {
		return rate;
	}

	public String getComment() {
		return comment;
	}

	public String getToken() {
		return token;
	}

	public String getTaskId() {
		return taskId;
	}

	public String getAuthToken() {
		return authToken;
	}
}
