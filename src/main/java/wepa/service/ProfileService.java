package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.*;
import wepa.repository.*;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    
    @Transactional
    public Profile createProfileAndAssignToUser(Account account) {
        account = accountRepository.findOne(account.getId());
        if (account == null) {
            return null;
        }
        //System.out.println("Account after repo find @ createProfileAndAssignToUser(Account account)" + account);
        //System.out.println("Account ID after repo find @ createProfileAndAssignToUser(Account account)" + account.getId());
        // why is initializing in a try-catch?
        /*Profile profile = null;
         while(profile == null) {
         try {
         profile = new Profile();
         } catch (Exception e) {
         System.out.println(e.getMessage());
         }
         }*/
        Profile profile = new Profile();
        profile = profileRepository.save(profile);
        account.addProfile(profile);
        account = accountRepository.save(account);
        profile.setOwnerAccount(account);
        profile = profileRepository.save(profile);
        account = accountRepository.save(account);
        //System.out.println("Profile for account " + account +
        //        " after repo save @ createProfileAndAssignToUser(Account account): " + account.getProfile());
        return profile;
    }

    public boolean setProfileActive(String profileId) {
        Profile profile = profileRepository.findOne(profileId);
        if (profile == null) {
            return false;
        }
        // Since only one profile can be active at a time, all other profiles
        // are set inactive.
        List<Profile> profiles = profileRepository.findByOwnerAccount(profile.getOwnerAccount());
        profiles.stream().forEach((p) -> {
            if (p.getId().equals(profileId)) {
                p.setActive(true);
            } else {
                p.setActive(false);
            }
        });
        return true;
    }

    public Profile findOne(Profile profile) {
        return profileRepository.findOne(profile.getId());
    }
    
    public List<Profile> findProfilesByAccount(Account account) {
        return profileRepository.findByOwnerAccount(account);
    }
    
    public Profile findProfileById(String id) {
        return profileRepository.findOne(id);
    }

    /*@Transactional
     public Profile assignQuestionToProfile(Profile profile, Question question, AnswerOption answer) {
     //System.out.println("Param Profile ID @ assignQuestionToProfile(Profile profile, dbQuestion dbQuestion): " +
     //        profile.getId());
     profile = profileRepository.findOne(profile.getId());

     if (profile == null) {
     return null;
     }
     //System.out.println("Param Answer @ assignQuestionToProfile(Profile profile, dbQuestion dbQuestion, AnswerOption answer): " +
     //        answer.getAnswerText());
     answer = answerOptionRepository.findOne(answer.getId());
     if (answer == null) {
     return null;
     }
     //System.out.println("Profile ID after repo find @ assignQuestionToProfile(Profile profile, dbQuestion dbQuestion): "
     //        + profile.getId());
     //System.out.println("Param dbQuestion ID @ assignQuestionToProfile(Profile profile, dbQuestion dbQuestion): "
     //        + dbQuestion.getId());
     question = questionRepository.findOne(question.getId());
     if (question == null) {
     return null;
     }

     //System.out.println("dbQuestion ID after repo save @ assignQuestionToProfile(Profile profile, dbQuestion dbQuestion): "
     //        + dbQuestion.getId());
        
     // Check if the current question is already in the profile
        
     ProfileQuestion profileQuestion = new ProfileQuestion();
     profileQuestion.setProfile(profile);
     profileQuestion.setQuestion(question);
     profileQuestion.setCorrectAnswer(answer);
     profileQuestion = profileQuestionRepository.save(profileQuestion);
     List<ProfileQuestion> profileQuestions = profile.getProfileQuestions();
     profileQuestions.add(profileQuestion);
     profile.setProfileQuestions(profileQuestions);
     return profileRepository.save(profile);
     }

     @Transactional
     public Profile assignQuestionListToProfile(Profile profile, List<DBQuestion> DBQuestions) {
     profile = profileRepository.findOne(profile.getId());
     List<ProfileQuestion> profileQuestions = profile.getProfileQuestions();
     for (DBQuestion DBQuestion : DBQuestions) {
     DBQuestion = questionRepository.findOne(DBQuestion.getId());
     ProfileQuestion profileQuestion = new ProfileQuestion();
     profileQuestion.setProfile(profile);
     profileQuestion.setDbQuestion(DBQuestion);
     profileQuestion = profileQuestionRepository.save(profileQuestion);
     profileQuestions.add(profileQuestion);
     }
     profile.setProfileQuestions(profileQuestions);
     return profileRepository.save(profile);
     }*/
}
