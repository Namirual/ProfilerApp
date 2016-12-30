package wepa.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.*;
import wepa.repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@org.springframework.context.annotation.Profile("default")
public class DeleteTest {
    private ProfileQuestion profileQuestion1;
    private ProfileQuestion profileQuestion2;
    private ProfileQuestion profileQuestion3;
    private ProfileQuestion profileQuestion4;
    private ProfileQuestion profileQuestion5;

    private Answer answer1;
    private Answer answer2;
    private Answer answer3;
    private Answer answer4;
    private Answer answer5;

    private Account pertti;
    private Account martti;

    private Profile profile1;
    private Profile profile2;
    private Profile profile3;

    private Question question1;
    private Question question2;
    private Question question3;
    private Question question4;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ImageObjectRepository imageObjectRepository;
    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private ProfileQuestionService profileQuestionService;
    @Autowired
    private AnswerOptionRepository answerOptionRepository;
    @Autowired
    private QuestionRepository questionRepository;
//    @Transactional
    @Before
    public void setUp() throws Exception {
        pertti = new Account();
        pertti.setName("Pertti");
        pertti.setEmail("pertti@foomail.com");
        pertti.setUsername("perrti");
        pertti.setAuthorities(Arrays.asList("ADMIN"));
        pertti.setPassword("pertinSalasana");
        pertti = accountRepository.save(pertti);
        martti = new Account();
        martti.setName("Martti");
        martti.setEmail("martti@foomail.com");
        martti.setUsername("marrti");
        martti.setAuthorities(Arrays.asList("USER"));
        martti.setPassword("martinSalasana");
        martti = accountRepository.save(martti);

        profile1 = profileService.createProfileAndAssignToUser(pertti);
        profile2 = profileService.createProfileAndAssignToUser(pertti);
        profile3 = profileService.createProfileAndAssignToUser(martti);

        question1 = questionService.createQuestion("How old is this person?", Arrays.asList("Still a child", "20-25", "25-30", "30-35", "35-40", "old as fuck"));
        question2 = questionService.createQuestion("How tall is this person?", Arrays.asList("Midget", "140-150", "150-160", "160-170", "170-180", "180-190", "190-200", "HUGE"));
        question3 = questionService.createQuestion("Does this person look like a dog or a cat person?", Arrays.asList("Neither", "Dog", "Cat", "Both"));
        question4 = questionService.createQuestion("Does this person look like Bitch?", Arrays.asList("No", "Yes", "What?"));

        profileQuestion1 = profileQuestionService.assignQuestionToProfile(profile1.getId(), question1.getId(), question1.getAnswerOptions().get(0).getId());
        profileQuestion2 = profileQuestionService.assignQuestionToProfile(profile2.getId(), question2.getId(), question2.getAnswerOptions().get(0).getId());
        profileQuestion3 = profileQuestionService.assignQuestionToProfile(profile3.getId(), question3.getId(), question3.getAnswerOptions().get(0).getId());
        profileQuestion4 = profileQuestionService.assignQuestionToProfile(profile1.getId(), question4.getId(), question4.getAnswerOptions().get(0).getId());
        profileQuestion5 = profileQuestionService.assignQuestionToProfile(profile2.getId(), question1.getId(), question1.getAnswerOptions().get(1).getId());

        answer1 = answerService.answerAllQuestions(pertti, Arrays.asList(profileQuestion3), Arrays.asList(profileQuestion3.getQuestion().getAnswerOptions().get(0))).get(0);
        List<Answer> answers = new ArrayList<>();
        answers = answerService.answerAllQuestions(martti, Arrays.asList(profileQuestion1, profileQuestion2, profileQuestion4, profileQuestion5),
                Arrays.asList(profileQuestion1.getQuestion().getAnswerOptions().get(0),profileQuestion2.getQuestion().getAnswerOptions().get(0),
                        profileQuestion4.getQuestion().getAnswerOptions().get(0), profileQuestion5.getQuestion().getAnswerOptions().get(0)));

        answer2 = answers.get(0);
        answer3 = answers.get(1);
        answer4 = answers.get(2);
        answer5 = answers.get(3);
    }
//    @Transactional
    @After
    public void tearDown() throws Exception {
//        accountRepository.deleteAll();
//        profileRepository.deleteAll();
//
//        answerRepository.deleteAll();
//        profileQuestionRepository.deleteAll();
//        answerOptionRepository.deleteAll();
//        questionRepository.deleteAll();
//        profileRepository.deleteAll();
        System.out.println(profileRepository.findOne(Long.parseLong("37")));

        profileRepository.deleteAll();
        answerRepository.deleteAll();
        profileQuestionRepository.deleteAll();
        answerOptionRepository.deleteAll();
        questionRepository.deleteAll();
        accountRepository.deleteAll();
        imageObjectRepository.deleteAll();

    }


    @Transactional
    @Test
    public void deleteProfileDeletesProfilesAndProfileQuestionsAndAnswersButNotAccounts() throws Exception {
        int profiles = profileRepository.findAll().size();
        System.out.println("Profiles before delete: " + profiles);
        int accounts = accountRepository.findAll().size();
        System.out.println("Accounts before delete: " + accounts);
        int profileQuestions = profileQuestionRepository.findAll().size();
        System.out.println("ProfileQuestions before delete: " + profileQuestions);
        int answers = answerRepository.findAll().size();
        System.out.println("Answers before delete: " + answers);

        profile1 = profileRepository.findOne(profile1.getId());
        profile2 = profileRepository.findOne(profile2.getId());
        profile3 = profileRepository.findOne(profile3.getId());

        profileRepository.delete(profile1.getId());

        int profiles1 = profileRepository.findAll().size();
        System.out.println("Profiles after 1st delete: " + profiles1);
        int accounts1 = accountRepository.findAll().size();
        System.out.println("Accounts after 1st delete: " + accounts1);
        int profileQuestions1 = profileQuestionRepository.findAll().size();
        System.out.println("ProfileQuestions after 1st delete: " + profileQuestions1);
        int answers1 = answerRepository.findAll().size();
        System.out.println("Answers after 1st delete: " + answers1);

        assertEquals(accounts, accounts1);
        assertNotEquals(profiles, profiles1);
        assertNotEquals(profileQuestions, profileQuestions1);
        assertNotEquals(answers, answers1);

        profileRepository.delete(profile2.getId());

        int profiles2 = profileRepository.findAll().size();
        System.out.println("Profiles after 2nd delete: " + profiles2);
        int accounts2 = accountRepository.findAll().size();
        System.out.println("Accounts after 2nd delete: " + accounts2);
        int profileQuestions2 = profileQuestionRepository.findAll().size();
        System.out.println("ProfileQuestions after 2nd delete: " + profileQuestions2);
        int answers2 = answerRepository.findAll().size();
        System.out.println("Answers after 2nd delete: " + answers2);

        assertEquals(accounts, accounts2);
        assertNotEquals(profiles1, profiles2);
        assertNotEquals(profileQuestions1, profileQuestions2);
        assertNotEquals(answers1, answers2);

        profileRepository.delete(profile3.getId());

        int profiles3 = profileRepository.findAll().size();
        System.out.println("Profiles after 3rd delete: " + profiles3);
        int accounts3 = accountRepository.findAll().size();
        System.out.println("Accounts after 3rd delete: " + accounts3);
        int profileQuestions3 = profileQuestionRepository.findAll().size();
        System.out.println("ProfileQuestions after 3rd delete: " + profileQuestions3);
        int answers3 = answerRepository.findAll().size();
        System.out.println("Answers after 3rd delete: " + answers3);

        assertEquals(accounts, accounts3);
        assertNotEquals(profiles2, profiles3);
        assertNotEquals(profileQuestions2, profileQuestions3);
        assertNotEquals(answers2, answers3);
    }


}