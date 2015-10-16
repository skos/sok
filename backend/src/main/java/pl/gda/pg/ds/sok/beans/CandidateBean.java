package pl.gda.pg.ds.sok.beans;

import java.util.UUID;

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
