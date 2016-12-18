package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.*;
import wepa.repository.AccountRepository;
import wepa.repository.ProfileQuestionRepository;
import wepa.repository.ProfileRepository;
import wepa.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;
    @Autowired
    private QuestionRepository questionRepository;

    public List<Account> getAllUsers() {
        return accountRepository.findAll();
    }

    public Account getUserById(String id) {
        return accountRepository.findOne(id);
    }

    public Profile getProfileById(String id) {
        return profileRepository.findOne(id);
    }
    public Profile getProfileByUserId(String userId) {
//        return profileRepository.findByOwnerAccount(accountRepository.findOne(userId));
        return null;
    }

    public List<DBQuestion> getQuestionsForProfileByUserId(String userId) {
        List<DBQuestion> DBQuestions = new ArrayList<>();
        Account user = accountRepository.findOne(userId);
        if(user.getProfile() != null) {
            List<ProfileQuestion> profileQuestions = user.getProfile().getProfileQuestions();
            for (ProfileQuestion profileQuestion : profileQuestions) {
                DBQuestions.add(profileQuestion.getDbQuestion());
            }
        }
        System.out.println("DBQuestions @ getQuestionsForProfileByUserId(String userId): " + DBQuestions);
        for (DBQuestion q : DBQuestions) {
            System.out.println("DBQuestion's content @ getQuestionsForProfileByUserId(String userId): " + q.getContent());
            System.out.println("DBQuestion's answer options @ getQuestionsForProfileByUserId(String userId): "
                    + q.getAnswerOptions());
        }

        return DBQuestions;
    }

    public Map<DBQuestion, AnswerOption> getQuestionsAndAnswersForProfileByUserId(String userId) {
        Map<DBQuestion, AnswerOption> questionsAndAnswers = new HashMap<>();
        Account user = accountRepository.findOne(userId);
        if(user.getProfile() != null) {
            List<ProfileQuestion> profileQuestions = user.getProfile().getProfileQuestions();
            for (ProfileQuestion profileQuestion : profileQuestions) {
                System.out.println("Profilequestion's question: " + profileQuestion.getDbQuestion().getContent());
                System.out.println("Profilequestion's Answer: " + profileQuestion.getAnswer().getAnswerText());
                questionsAndAnswers.put(profileQuestion.getDbQuestion(), profileQuestion.getAnswer());
            }
        }
        System.out.println("Questions @ getQuestionsForProfileByUserId(String userId): " + questionsAndAnswers.keySet());
        for (DBQuestion q : questionsAndAnswers.keySet()) {
            System.out.println("DBQuestion's content @ getQuestionsForProfileByUserId(String userId): " + q.getContent());
            System.out.println("DBQuestion's answer options @ getQuestionsForProfileByUserId(String userId): "
                    + q.getAnswerOptions());
            System.out.println("DBQuestion's answer @ getQuestionsForProfileByUserId(String userId): "
                    + questionsAndAnswers.get(q));
        }

        return questionsAndAnswers;
    }
}
