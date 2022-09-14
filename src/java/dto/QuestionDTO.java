package dto;

import java.io.Serializable;

// Responsible to save information about a question that can be used inside the application.
public class QuestionDTO implements Serializable {

    private int questionId;
    private String questionText;
    private String questionOptions;
    private String questionAnswers;

    public QuestionDTO() {
    }

    public int getQuestionId() {
        return this.questionId;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public String getQuestionOptions() {
        return this.questionOptions;
    }

    public String getQuestionAnswers() {
        return this.questionAnswers;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setQuestionOptions(String questionOptions) {
        this.questionOptions = questionOptions;
    }

    public void setQuestionAnswers(String questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    @Override
    public String toString() {
        return String.format("Question id: %d, Question text: %s, Question options: %s, Question answers: %s",
                this.questionId, this.questionText, this.questionOptions, this.questionAnswers);
    }

}
