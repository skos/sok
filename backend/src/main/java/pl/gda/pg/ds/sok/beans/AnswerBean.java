package pl.gda.pg.ds.sok.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerBean {

	private String content;
	private String token;
	private String taskId;
	
	public String getContent() {
		return content;
	}
	
	public String getToken() {
		return token;
	}
	
	public Long getTaskId() {
		return Long.parseLong(taskId);
	}
}
