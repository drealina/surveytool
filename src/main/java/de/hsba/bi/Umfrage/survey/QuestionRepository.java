package de.hsba.bi.Umfrage.survey;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findBySurveyId(Long surveyId);
}
