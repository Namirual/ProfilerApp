package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.*;
import wepa.repository.*;

import java.util.Arrays;
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
    public Profile assignQuestionToProfile(Profile profile, Question question, AnswerOption answer) {
        System.out.println("Param Profile ID @ assignQuestionToProfile(Profile profile, Question question): " +
        profile.getId());
        profile = profileRepository.findOne(profile.getId());
        System.out.println("Param Answer @ assignQuestionToProfile(Profile profile, Question question, AnswerOption answer): " +
        answer.getAnswerText());
        answer = answerOptionRepository.findOne(answer.getId());
        System.out.println("Profile ID after repo find @ assignQuestionToProfile(Profile profile, Question question): "
                + profile.getId());
        System.out.println("Param Question ID @ assignQuestionToProfile(Profile profile, Question question): "
                + question.getId());
        question = questionRepository.findOne(question.getId());
        System.out.println("Question ID after repo save @ assignQuestionToProfile(Profile profile, Question question): "
                + question.getId());
        ProfileQuestion profileQuestion = new ProfileQuestion();
        profileQuestion = profileQuestionRepository.save(profileQuestion);
        profileQuestion.setProfile(profile);
        profileQuestion.setQuestion(question);
        profileQuestion.setAnswer(answer);
        profileQuestion = profileQuestionRepository.save(profileQuestion);
        List<ProfileQuestion> profileQuestions = profile.getProfileQuestions();
        profileQuestions.add(profileQuestion);
        profile.setProfileQuestions(profileQuestions);
        return profileRepository.save(profile);
    }
    @Transactional
    public Profile assignQuestionListToProfile(Profile profile, List<Question> questions) {
        profile = profileRepository.findOne(profile.getId());
        List<ProfileQuestion> profileQuestions = profile.getProfileQuestions();
        for (Question question : questions) {
            question = questionRepository.findOne(question.getId());
            ProfileQuestion profileQuestion = new ProfileQuestion();
            profileQuestion.setProfile(profile);
            profileQuestion.setQuestion(question);
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
