package pl.gda.pg.ds.sok.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingBean {

	private int rating;
	private String comment;
	private String token;
	private String taskId;
	private String authToken;
	private String authorName;
	private boolean isFresh;

	public RatingBean() {
	}

	public RatingBean(int rating, String comment, String authorName, boolean isFresh) {
		this.rating = rating;
		this.comment = comment;
		this.authorName = authorName;
		this.isFresh = isFresh;
	}

	public int getRating() {
		return rating;
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

	public String getAuthorName() {
		return authorName;
	}

	public boolean isFresh() {
		return isFresh;
	}
}
