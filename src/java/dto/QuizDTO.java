package dto;

import java.io.Serializable;

// Responsible to save information about a quiz that can be used inside the application.
public class QuizDTO implements Serializable {

    private int quizId;
    private String quizSubject;

    public QuizDTO() {
    }

    public int getQuizId() {
        return this.quizId;
    }

    public String getQuizSubject() {
        return this.quizSubject;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setQuizSubject(String quizSubject) {
        this.quizSubject = quizSubject;
    }

    @Override
    public String toString() {
        return String.format("Nr: %d, Subject: %s",
                this.quizId, this.quizSubject);
    }
}
