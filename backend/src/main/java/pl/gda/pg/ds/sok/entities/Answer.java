package pl.gda.pg.ds.sok.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "answers")
public class Answer implements Serializable  {
	
	private static final long serialVersionUID = -5730373439063907932L;
	
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
	
	public Answer() {
	}
	
	public Answer(String content, Candidate candidate, Task task, String ip) {
		this.content = content;
		this.candidate = candidate;
		this.task = task;
		this.ip = ip;
		this.answerDate = new Date();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getContent() {
		return content;
	}
	
	public Candidate getCandidate() {
		return candidate;
	}
	
	public Task getTask() {
		return task;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
