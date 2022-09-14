package dto;

import java.io.Serializable;

// Responsible to save information about a result that can be used inside the application.
public class ResultDTO implements Serializable {

    private int resultId;
    private String subject;
    private int resultScore;

    public ResultDTO() {
    }

    public int getResultId() {
        return this.resultId;
    }

    public String getSubject() {
        return this.subject;
    }

    public int getResultScore() {
        return this.resultScore;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setResultScore(int resultScore) {
        this.resultScore = resultScore;
    }

    @Override
    public String toString() {
        return String.format("Quiz about: %s, Result score: %d",
                 this.subject, this.resultScore);
    }

}
