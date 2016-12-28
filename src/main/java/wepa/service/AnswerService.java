package wepa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    public List<Answer> getAnswersForProfile(Long profileId) {
        Profile profile = profileRepository.findOne(profileId);
        List<ProfileQuestion> questions = profile.getProfileQuestions();
        List<Answer> answers = answerRepository.findByProfileQuestion(questions);
        return answers;
    }
    
    public List<Answer> getUserAnswersForProfile(Long profileId, Account account) {
        Profile profile = profileRepository.findOne(profileId);
        List<ProfileQuestion> questions = profile.getProfileQuestions();
        List<Answer> answers = answerRepository.findByProfileQuestionAndAccount(questions, account);
        return answers;
    }
    
    public Map<ProfileQuestion,Map<AnswerOption,Integer>> calculateAnswerRatesForProfileQuestions(Long profileId) {
        // create a map where each question maps to all answeroptions and all answeroptions map to ints
        Map<ProfileQuestion,Map<AnswerOption,Integer>> rateMap = new HashMap<>();
        Profile profile = profileRepository.findOne(profileId);
        List<ProfileQuestion> questions = profile.getProfileQuestions();
        List<Answer> answers = answerRepository.findByProfileQuestion(questions);
        // loop the questions
        for(ProfileQuestion question : questions) {
            // create a key for each question
            rateMap.put(question, new HashMap<>());
            // count number of answers for each question
            int ansCount = 0;
            // loop through the answers
            for(Answer answer : answers) {
                // if it is an answer for the current question then
                if(question==answer.getProfileQuestion()) {
                    // add to the answer count
                    ansCount++;
                    // and map the question to an answerOption key, which
                    // maps to the number of answers with this option
                    rateMap.get(question).put(answer.getAnswer(), rateMap.get(question).get(answer.getAnswer())+1);
                }
            }
            // loop again trhough the answerOptions for this question
            for(AnswerOption option : rateMap.get(question).keySet()) {
                // now count what is the percentage of this answeOption
                rateMap.get(question).put(option, rateMap.get(question).get(option)*100/ansCount);
            }
        }
        return rateMap;
    }
    
    public boolean hasUserAnswered(List<ProfileQuestion> questions, Account account) {
        return answerRepository.findByProfileQuestionAndAccount(questions, account)!=null;
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
