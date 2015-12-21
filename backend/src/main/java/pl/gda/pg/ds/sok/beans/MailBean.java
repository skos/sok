package pl.gda.pg.ds.sok.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MailBean {
    private String minAnswers;
    private String maxAnswers;
    private String subject;
    private String content;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMinAnswers() {
        return minAnswers;
    }

    public void setMinAnswers(String minAnswers) {
        this.minAnswers = minAnswers;
    }

    public String getMaxAnswers() {
        return maxAnswers;
    }

    public void setMaxAnswers(String maxAnswers) {
        this.maxAnswers = maxAnswers;
    }
}
