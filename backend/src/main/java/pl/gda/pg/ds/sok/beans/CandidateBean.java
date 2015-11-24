package pl.gda.pg.ds.sok.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateBean {

	private String token;
	private String name;
	private String email;
	private boolean canAdmin;
	
	public CandidateBean() {
		this.token = UUID.randomUUID().toString();
	}

	public CandidateBean(String token, String name, String email, boolean canAdmin) {
		this.token = token;
		this.name = name;
		this.email = email;
		this.canAdmin = canAdmin;
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

	public boolean isCanAdmin() {
		return canAdmin;
	}
}
