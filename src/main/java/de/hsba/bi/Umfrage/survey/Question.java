package de.hsba.bi.Umfrage.survey;

import javax.persistence.*;
import java.util.List;


@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String question;

    @Basic(optional = false)
    private Long surveyId;

        //bei den Antorten nicht "weiß nicht" als Möglichkeit bei jeder Frage hinzufügen
    @Basic(optional = false)
    private int dontKnowAnswer = 0;

    @Transient
    private List<Answer> answers;


    //Getter und Setter
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }

    public void setQuestion(String question) { this.question = question; }

    public Long getSurveyId() { return surveyId; }

    public void setSurveyId(Long surveyId) { this.surveyId = surveyId; }

    public List<Answer> getAnswers() { return answers; }

    public void setAnswers(List<Answer> answers) { this.answers = answers; }

    public int getDontKnowAnswer() { return dontKnowAnswer; }

    public void setDontKnowAnswer(int dontKnowAnswer) { this.dontKnowAnswer = dontKnowAnswer; }

    //Defaul Konstruktor
    public Question() {
    }

    //Konstruktor
    public Question(String question, Long surveyId) {
        this.question = question;
        this.surveyId = surveyId;
        this.dontKnowAnswer = 0;
    }

    public Question(String question) {
    }


}
