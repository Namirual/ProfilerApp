package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.AnswerOption;
import wepa.repository.AnswerOptionRepository;
import wepa.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import wepa.domain.Question;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerOptionRepository answerOptionRepository;

//    public DBQuestion createQuestion(List<AnswerOption> answerOptions) {
//        DBQuestion question = new DBQuestion();
//        question.setAnswerOptions(answerOptions);
//        return questionRepository.save(question);
//    }
    @Transactional
    public Question createQuestion(String content, List<String> optionTexts) {
        // if the question already exists, don't create a duplicate.
        if (questionRepository.findByContent(content) != null) {
            return questionRepository.findByContent(content);
        }
        Question question = new Question();
        question.setContent(content);
        
        // create the array of answerOptions for this question
        List<AnswerOption> answerOptions = new ArrayList<>();
        for (String text : optionTexts) {
            AnswerOption answerOption = new AnswerOption();
            answerOption.setOrderNumber(optionTexts.indexOf(text));
            answerOption.setAnswerText(text);
            answerOption.setQuestion(question);
            
            answerOption = answerOptionRepository.save(answerOption);
            answerOptions.add(answerOption);
        }
        
        // add answerOptions and save the Question.
        question.setAnswerOptions(answerOptions);
        question = questionRepository.save(question);
        //System.out.println("Answer options for dbQuestion @ QuestionService.createQuestion(Map<Integer, String> answerOptionMap) " + question.getAnswerOptions());
        return question;
    }
    
    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }
    
    public Question findOneQuestion(Long id) {
        return questionRepository.findOne(id);
    }
    
    public List<Question> findManyQuestions(List<Long> id) {
        return questionRepository.findByIdIn(id);
    }
}
