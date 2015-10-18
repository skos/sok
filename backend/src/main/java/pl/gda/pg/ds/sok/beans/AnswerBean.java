package pl.gda.pg.ds.sok.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerBean {

	private String content;
	private String candidateId;
	private String taskId;
	
	public String getContent() {
		return content;
	}
	
	public Long getCandidateId() {
		return Long.parseLong(candidateId);
	}
	
	public Long getTaskId() {
		return Long.parseLong(taskId);
	}
}
