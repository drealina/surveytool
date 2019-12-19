package de.hsba.bi.Umfrage.web.form;

import de.hsba.bi.Umfrage.survey.*;
import de.hsba.bi.Umfrage.user.UserAdapter;
import de.hsba.bi.Umfrage.user.UserAdapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class SurveyController {

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserAdapterService userAdapterService;

    @Autowired
    AnswerRepository answerRepository;


    //alle Controller-Funktionen zur Umfrage
    //Gibt eine Liste aller freigeschalten Umfragen aus
    @GetMapping("/surveys")
    public String showPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("principal", auth.getPrincipal());
        model.addAttribute("data",
                surveyRepository.findByActive(true, PageRequest.of(page, 5)));
        model.addAttribute("currentPage", page);
        return "surveys";
    }

    //Gibt eine Liste der Umfragen des angemeldeten Useres aus
    @GetMapping("/mysurveys")
    public String showMyPage(Model model, @RequestParam(defaultValue = "0") int page) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserAdapter userAdapter = userAdapterService.getUserAdapterbyUsername(auth.getName());
        Long userId = userAdapter.getUser().getId();
        model.addAttribute("userId", userId);
      //  model.addAttribute("data", surveyRepository.findAll(PageRequest.of(page, 5)));
        model.addAttribute("data", surveyRepository.findByUserId(userId,PageRequest.of(page, 5)));
        model.addAttribute("currentPage", page);
        return "mySurveys";
    }

    //neue Umfrage anlegen
    @PostMapping("/surveys/save")
    public String save(Survey survey) {
        surveyRepository.save(survey);
        return "redirect:/mysurveys";
    }

    //Umfrage Löschen mit den dazugehörigen Fragen und Antworten
    @GetMapping("/delete")
    public String deleteSurvey(long id) {
        Optional<Survey> osurvey = this.surveyRepository.findById(id);
        if(osurvey.isPresent()){
            List<Question> lquestion = this.questionRepository.findBySurveyId(id);
            for (Question question: lquestion) {
                List<Answer> lanswer = this.answerRepository.findByQuestionId(question.getId());
                for (Answer answer: lanswer) {
                    answerRepository.deleteById(answer.getId());
                }
                questionRepository.deleteById(question.getId());
            }
            surveyRepository.deleteById(id);
        }
        return "redirect:/mysurveys";
    }

    //Umfrage  nach der ID finden
   @GetMapping("surveys/findOne")
    @ResponseBody
     public Survey findOne(long id) {
     return surveyRepository.findById(id).get();
    }

    //eine Umfrage sperren
    @GetMapping("disable")
    public String  disableSurvey(@RequestParam(defaultValue = "0") long surveyId) {
        Optional<Survey> survey = this.surveyRepository.findById(surveyId);
        if (survey.isPresent()) {
            survey.get().setActive(false);
            save(survey.get());
        }
        return "redirect:/mysurveys";
    }

    //eine Umfrage freigeben
    // wenn eine Umfrage min. eine Frage (positive Bedingung) hat und alle Fragen min. zwei Antworten(negativ Bedingung) haben
    @GetMapping("enable")
    public String enableSurvey(@RequestParam(defaultValue = "0")long surveyId) {
        Optional<Survey> survey = this.surveyRepository.findById(surveyId);
        if (survey.isPresent()) {
            List<Question> questions = this.questionRepository.findBySurveyId(surveyId);
            if(questions.size()>0) {
                for (Question question:questions) {
                    List<Answer> answers = this.answerRepository.findByQuestionId(question.getId());
                    if(answers.size()<2){
                        return "redirect:/mysurveys";
                    }
                }

                survey.get().setActive(true);
                save(survey.get());
            }
        }
        return "redirect:/mysurveys";
    }

    //eine Umfrage bearbeiten können
    @GetMapping("editSurvey")
    public String editSurvey(Model model, long id){
        Optional<Survey> survey = this.surveyRepository.findById(id);
        if (survey.isPresent()){
            model.addAttribute("survey", survey.get());
            List<Question> questions = questionRepository.findBySurveyId(id);
            for (Question question:questions) {
                question.setAnswers(answerRepository.findByQuestionId(question.getId()));
            }
            model.addAttribute("questions", questions);

            return "editsurvey";
        }
    return "redirect:/mysurveys";
}



//eine Umfrage (Name und Bschreibung) tatsächlich ändern/bearbeiten
    @PostMapping("changeSurvey")
    public String changeSurvey(Model model, long id, String name, String description){
        Optional<Survey> survey = this.surveyRepository.findById(id);
        if (survey.isPresent()){
            Survey surv = survey.get();
            surv.setName(name);
            surv.setDescription(description);
            surveyRepository.save(surv);
            return "redirect:/editSurvey/?id="+id;
        }
        return "redirect:/mysurveys";
    }

    //Teilnehmen
    @GetMapping("teilnehmen")
    public String teilnehmen(){
        return "teilnehmen";
    }

    //AUswertung
    @GetMapping("auswertung")
    public String auswertung(){
        return "auswertung";
    }


   //Fragen zu einer Umfrage hinzufügen
    @PostMapping("addQuestion")
    public String addQuestion(long surveyId, String question) {
           questionRepository.save(new Question(question, surveyId));
        return "redirect:/editSurvey/?id="+surveyId;
    }


    //Frage editieren
    @PostMapping("changeQuestion")
    public String changeQuestion(Model model, long id, String question){
        Optional<Question> oquestion = this.questionRepository.findById(id);
        if (oquestion.isPresent()){
            Question ques = oquestion.get();
            ques.setQuestion(question);
            questionRepository.save(ques);
            return "redirect:/editSurvey/?id="+ques.getSurveyId();
        }
        return "redirect:/mysurveys";
    }

    //Fragenfeld vorbelegen beim Bearbeiten
    @GetMapping ("/getOneQuestion")
    @ResponseBody
    public Question getOneQuestion(long id){
        Optional<Question> oquestion = this.questionRepository.findById(id);
        if(oquestion.isPresent()){
            return oquestion.get();
        }
        return null;
    }


    //Frage löschen und die dazugehörigen Antworten auch
    @GetMapping("/deleteQuestion")
    public String deleteQuestion(long id) {
        Optional<Question> oquestion = this.questionRepository.findById(id);
        if (oquestion.isPresent()) {
            Question ques = oquestion.get();
            List<Answer> lanswer = this.answerRepository.findByQuestionId(id);
            for (Answer answer:lanswer) {
                answerRepository.deleteById(answer.getId());
            }
            questionRepository.deleteById(id);
            return "redirect:/editSurvey?id=" + ques.getSurveyId();
        }
        return "redirect:/mysurveys";
    }


    //Antwort zu einer Frage hinzufügen
    @PostMapping("addAnswer")
    public String addAnswer(long questionId, String answer) {
        answerRepository.save(new Answer(answer, questionId));
       Optional<Question> oquestion = questionRepository.findById(questionId);
       if (oquestion.isPresent()){
           return "redirect:/editSurvey/?id="+oquestion.get().getSurveyId();
       }
        return "redirect:/mysurveys";
    }


    //Antwort editieren
    @PostMapping("changeAnswer")
    public String changeAnswer(Model model, long id, String answer){
        Optional<Answer> oanswer = this.answerRepository.findById(id);
        if (oanswer.isPresent()){
            Answer ans = oanswer.get();
            ans.setAnswer(answer);
            answerRepository.save(ans);
            Optional<Question> oquestion = questionRepository.findById(ans.getQuestionId());
            if(oquestion.isPresent()){
                return "redirect:/editSurvey/?id="+oquestion.get().getSurveyId();
            }
        }
        return "redirect:/mysurveys";
    }

    //Antwortenfeld vorbelegen beim Bearbeiten
    @GetMapping ("/getOneAnswer")
    @ResponseBody
    public Answer getOneAnswer(long id){
        Optional<Answer> oanswer = this.answerRepository.findById(id);
        if(oanswer.isPresent()){
            return oanswer.get();
        }
        return null;
    }


    //Antwort löschen
    @GetMapping("/deleteAnswer")
    public String deleteAnswer(long id) {
        Optional<Answer> oanswer = this.answerRepository.findById(id);
        if (oanswer.isPresent()) {
            Answer ans = oanswer.get();
            answerRepository.deleteById(id);
            Optional<Question> oquestion = questionRepository.findById(ans.getQuestionId());
            if (oanswer.isPresent()) {
            Question ques = oquestion.get();
            return "redirect:/editSurvey?id=" + ques.getSurveyId();
        }
    }
        return "redirect:/mysurveys";
    }


}
