package pl.gda.pg.ds.sok.beans.interfaces;

import pl.gda.pg.ds.sok.entities.Candidate;

public interface TaskBeanInterface {
    String getTitle();

    void setTitle(String title);

    String getType();

    void setType(String type);

    String getContent();

    void setContent(String content);

    Integer getDifficulty();

    void setDifficulty(Integer difficulty);

    Long getId();

    void setId(Long id);

    Candidate getCandidate();

    void setCandidate(Candidate candidate);
}
