package wepa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.Account;
import wepa.domain.Answer;
import wepa.domain.AnswerOption;
import wepa.domain.Profile;
import wepa.domain.ProfileQuestion;
import wepa.repository.AnswerRepository;
import wepa.repository.ProfileRepository;

@Service
public class AnswerService {
    
    @Autowired
    private AnswerRepository answerRepository;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    public List<Answer> getAnswersForProfile(String profileId) {
        Profile profile = profileRepository.findOne(profileId);
        List<ProfileQuestion> questions = profile.getProfileQuestions();
        List<Answer> answers = answerRepository.findByProfileQuestion(questions);
        return answers;
    }
    
    public Answer createAnswer(Account answerer, ProfileQuestion question, AnswerOption answer) {
        // is the question already answered? Should be controlled elsewhere.
        //if (answerRepository.findByProfileQuestionAndAccount(question, answerer) != null) {
        //    return answerRepository.findByProfileQuestionAndAccount(question, answerer);
        //}
        Answer ans = new Answer();
        ans.setAnswerer(answerer);
        ans.setProfileQuestion(question);
        ans.setAnswer(answer);
        ans.setPostTime(new Date());
        ans = answerRepository.save(ans);
        return ans;
    }
    
    public List<Answer> answerAllQuestions(Account answerer, List<ProfileQuestion> questions, List<AnswerOption> answers) {
        List <Answer> ans = new ArrayList<>();
        questions.stream().forEach((question) -> {
            ans.add(createAnswer(answerer, question, answers.get(questions.indexOf(question))));
        });
        return ans;
    }
    
    
}
