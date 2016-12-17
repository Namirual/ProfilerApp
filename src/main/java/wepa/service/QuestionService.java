package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.AnswerOption;
import wepa.domain.ProfileQuestion;
import wepa.domain.Question;
import wepa.repository.AnswerOptionRepository;
import wepa.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerOptionRepository answerOptionRepository;

//    public Question createQuestion(List<AnswerOption> answerOptions) {
//        Question question = new Question();
//        question.setAnswerOptions(answerOptions);
//        return questionRepository.save(question);
//    }
    @Transactional
    public Question createQuestion(String content, Map<Integer, String> answerOptionMap) {
        List<AnswerOption> answerOptions = new ArrayList<>();
        Question question = new Question();
        question.setContent(content);
        question = questionRepository.save(question);
        for (Integer orderNumber : answerOptionMap.keySet()) {
            AnswerOption answerOption = new AnswerOption();
            answerOption = answerOptionRepository.save(answerOption);
            answerOption.setOrderNumber(orderNumber);
            System.out.println("AnswerText @ createQuestion(String content, Map<Integer, String> answerOptionMap): "
                    + answerOptionMap.get(orderNumber));
            answerOption.setAnswerText(answerOptionMap.get(orderNumber));
            answerOption.setQuestion(question);
            answerOption = answerOptionRepository.save(answerOption);

            answerOptions.add(answerOption);
        }
        question.setAnswerOptions(answerOptions);
        question = questionRepository.save(question);
        System.out.println("Answer options for question @ QuestionService.createQuestion(Map<Integer, String> answerOptionMap) " + question.getAnswerOptions());
        return question;
    }
}
