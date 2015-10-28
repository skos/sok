package pl.gda.pg.ds.sok.entities;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "answershistory")
public class AnswerHistory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Type(type="text")
	private String content;
	@ManyToOne
	private Candidate candidate;
	@ManyToOne
	private Task task;
	@NotNull
	@Column(columnDefinition = "inet default '127.0.0.1'")
	@ColumnTransformer(write="CAST(? AS inet)")
	private String ip;
	@NotNull
	@Column(columnDefinition = "timestamp with time zone default now()")
	private Date answerDate;

	public AnswerHistory() {
	}

	public AnswerHistory(String content, Candidate candidate, Task task, String ip) {
		this.content = content;
		this.candidate = candidate;
		this.task = task;
		this.ip = ip;
		this.answerDate = new Date();
	}
}
