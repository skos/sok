package pl.gda.pg.ds.sok.entities;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "answershistory", indexes = {
		@Index(columnList = "answerdate", name = "answershistory_answerdate_idx")
})
public class AnswerHistory implements Serializable {

	private static final long serialVersionUID = 4597203050079960763L;

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
	@OneToMany(mappedBy="answer",cascade=CascadeType.PERSIST)
	private List<Rating> ratings = new ArrayList<>();

	public AnswerHistory() {
	}

	public AnswerHistory(String content, Candidate candidate, Task task, String ip) {
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

	public Date getAnswerDate() {
		return answerDate;
	}
}
