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
        System.out.println("Account after repo find @ createProfileAndAssignToUser(Account account)" + account);
        System.out.println("Account ID after repo find @ createProfileAndAssignToUser(Account account)" + account.getId());
        Profile profile = new Profile();
        profile = profileRepository.save(profile);
        account.setProfile(profile);
        account = accountRepository.save(account);
        profile.setOwnerAccount(account);
        profile = profileRepository.save(profile);
        account = accountRepository.save(account);
        System.out.println("Profile for account " + account +
                " after repo save @ createProfileAndAssignToUser(Account account): " + account.getProfile());
        return profile;
    }

    @Transactional
    public Profile assignQuestionToProfile(Profile profile, DBQuestion dbQuestion, AnswerOption answer) {
        System.out.println("Param Profile ID @ assignQuestionToProfile(Profile profile, dbQuestion dbQuestion): " +
                profile.getId());
        profile = profileRepository.findOne(profile.getId());

        if (profile == null) {
            return null;
        }
        System.out.println("Param Answer @ assignQuestionToProfile(Profile profile, dbQuestion dbQuestion, AnswerOption answer): " +
                answer.getAnswerText());
        answer = answerOptionRepository.findOne(answer.getId());
        if (answer == null) {
            return null;
        }
        System.out.println("Profile ID after repo find @ assignQuestionToProfile(Profile profile, dbQuestion dbQuestion): "
                + profile.getId());
        System.out.println("Param dbQuestion ID @ assignQuestionToProfile(Profile profile, dbQuestion dbQuestion): "
                + dbQuestion.getId());
        dbQuestion = questionRepository.findOne(dbQuestion.getId());
        if (dbQuestion == null) {
            return null;
        }

        System.out.println("dbQuestion ID after repo save @ assignQuestionToProfile(Profile profile, dbQuestion dbQuestion): "
                + dbQuestion.getId());
        ProfileQuestion profileQuestion = new ProfileQuestion();
        profileQuestion = profileQuestionRepository.save(profileQuestion);
        profileQuestion.setProfile(profile);
        profileQuestion.setDbQuestion(dbQuestion);
        profileQuestion.setAnswer(answer);
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
    }

    public Profile findOne(Profile profile) {
        return profileRepository.findOne(profile.getId());
    }
}
