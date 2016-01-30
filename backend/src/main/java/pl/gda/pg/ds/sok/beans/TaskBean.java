package pl.gda.pg.ds.sok.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import pl.gda.pg.ds.sok.beans.interfaces.TaskBeanInterface;
import pl.gda.pg.ds.sok.entities.Candidate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskBean implements TaskBeanInterface {

    private Long id;
    private String title;
    private String type;
    private String content;
    private Integer difficulty;
    private Candidate candidate;

    public TaskBean() {}

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Integer getDifficulty() {
        return difficulty;
    }

    @Override
    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Candidate getCandidate() {
        return candidate;
    }

    @Override
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
