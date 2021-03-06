package pl.gda.pg.ds.sok.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.mockito.stubbing.Answer;

@Entity
@Table(name = "tasks")
public class Task implements Serializable  {
	
	private static final long serialVersionUID = 6609599687003270487L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(max = 128)
	private String title;
	@NotNull
	@Size(max = 64)
	private String type;
	@NotNull
	@Type(type="text")
	private String content;
    @NotNull
	@Column(columnDefinition = "smallint default 1")
    private Integer difficulty;
	@OneToMany(mappedBy="task",cascade=CascadeType.PERSIST)
    private List<AnswerHistory> answers = new ArrayList<>();
	@ManyToOne
	private Candidate candidate;
	
	public Task() {
	}

	public Task(String title, String type, String content, Integer difficulty, Candidate candidate) {
		this.title = title;
		this.type = type;
		this.content = content;
		this.difficulty = difficulty;
		this.candidate = candidate;
	}
	
	public Task(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getType() {
		return type;
	}
	
	public String getContent() {
		return content;
	}

    public Integer getDifficulty() {
        return difficulty;
    }

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<AnswerHistory> getAnswers() {
		return answers;
	}
}
