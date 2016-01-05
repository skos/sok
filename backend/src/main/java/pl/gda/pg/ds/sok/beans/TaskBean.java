package pl.gda.pg.ds.sok.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import pl.gda.pg.ds.sok.entities.Candidate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskBean {

    private String title;
    private String type;
    private String content;
    private Integer difficulty;
    private Candidate candidate;

    public TaskBean() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
