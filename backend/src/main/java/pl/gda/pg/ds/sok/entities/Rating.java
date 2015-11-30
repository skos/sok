package pl.gda.pg.ds.sok.entities;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ratingshistory", indexes = {
		@Index(columnList = "date", name = "ratingshistory_date_idx")
})
public class Rating implements Serializable {

	private static final long serialVersionUID = 5045398735786695881L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(columnDefinition = "smallint default 1")
	private int rating;
	@NotNull
	@Type(type="text")
	private String comment;
	@NotNull
	@Column(columnDefinition = "inet default '127.0.0.1'")
	@ColumnTransformer(write="CAST(? AS inet)")
	private String ip;
	@NotNull
	@Column(columnDefinition = "timestamp with time zone default now()")
	private Date date;
	@ManyToOne
	private AnswerHistory answer;
	@ManyToOne
	private Candidate assessor;

	public Rating() {
	}

	public Rating(int rating, String comment, Candidate assessor, AnswerHistory answer, String ip) {
		this.rating = rating;
		this.comment = comment;
		this.assessor = assessor;
		this.answer = answer;
		this.ip = ip;
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public int getRating() {
		return rating;
	}

	public String getComment() {
		return comment;
	}

	public Long getAnswerId() {
		return answer.getId();
	}

	public String getAssessorName() {
		return assessor.getName();
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
