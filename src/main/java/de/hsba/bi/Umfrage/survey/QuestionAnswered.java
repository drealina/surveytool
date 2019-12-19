package de.hsba.bi.Umfrage.survey;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class QuestionAnswered {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private Long questionId;

    @Basic(optional = false)
    private Long answerId;

//Konstruktor
    public QuestionAnswered() {
    }
//Setter und Getter
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

}
