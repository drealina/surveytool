package de.hsba.bi.Umfrage.survey;

import javax.persistence.*;

@Entity
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String answer;

    @Basic(optional = false)
    private Long questionId;

    @Basic(optional = false)
    private int answeredCount = 0;

    //Getter und Setter

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getAnswer() { return answer; }

    public void setAnswer(String answer) { this.answer = answer; }

    public Long getQuestionId() { return questionId; }

    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public int getAnsweredCount() { return answeredCount; }

    public void setAnsweredCount(int answerCount) { this.answeredCount = answeredCount; }

    //Default Konstruktor
    public Answer() {
    }

    //Konstrutor
    public Answer(String answer, Long questionId) {
        this.answer = answer;
        this.questionId = questionId;
        this.answeredCount = 0;
    }

    public Answer(String answer) {
    }

}
