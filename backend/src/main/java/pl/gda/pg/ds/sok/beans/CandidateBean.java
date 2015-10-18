package pl.gda.pg.ds.sok.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateBean {

	private String token;
	private String name;
	private String email;
	
	public CandidateBean() {
		this.token = UUID.randomUUID().toString();
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getToken() {
		return token;
	}
}
