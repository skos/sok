package pl.gda.pg.ds.sok.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	
	public Answer() {
	}
	
	public Answer(String content, Candidate candidate, Task task) {
		this.content = content;
		this.candidate = candidate;
		this.task = task;
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
