package wepa.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wepa.domain.Account;
import wepa.domain.Profile;
import wepa.domain.ProfileQuestion;
import wepa.domain.Question;
import wepa.repository.*;
import wepa.service.AccountService;
import wepa.service.ProfileQuestionService;
import wepa.service.ProfileService;
import wepa.service.QuestionService;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@org.springframework.context.annotation.Profile("test")
public class ServiceIntegrationTest {

    private Account pertti;
    private Account martti;
    private Profile perttiProfile1;
    private Profile marttiProfile1;

    private Question question1;
    private Question question2;
    private Question question3;
    private Question question4;

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private AnswerOptionRepository answerOptionRepository;
    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ProfileQuestionService profileQuestionService;


    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < 50; i++) {
            questionService.createQuestion(UUID.randomUUID().toString(), Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        }
        question1 = questionService.createQuestion("How old is this person?", Arrays.asList("Still a child", "20-25", "25-30", "30-35", "35-40", "old as fuck"));
        question2 = questionService.createQuestion("How tall is this person?", Arrays.asList("Midget", "140-150", "150-160", "160-170", "170-180", "180-190", "190-200", "HUGE"));
        question3 = questionService.createQuestion("Does this person look like a dog or a cat person?", Arrays.asList("Neither", "Dog", "Cat", "Both"));
        question4 = questionService.createQuestion("Does this person look like Bitch?", Arrays.asList("No", "Yes", "What?"));

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
        perttiProfile1 = profileService.createProfileAndAssignToUser(pertti);
        marttiProfile1 = profileService.createProfileAndAssignToUser(martti);

    }

    @After
    public void tearDown() throws Exception {

        profileQuestionRepository.deleteAll();
        answerOptionRepository.deleteAll();
        questionRepository.deleteAll();
        profileRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void createdQuestionsExistInQuestionRepo() throws Exception {
        assertNotNull(questionRepository.findByContent("How old is this person?").getId());
        assertNotNull(questionRepository.findByContent("How tall is this person?").getId());
        assertNotNull(questionRepository.findByContent("Does this person look like a dog or a cat person?").getId());
        assertNotNull(questionRepository.findByContent("Does this person look like Bitch?").getId());
    }

    @Test
    public void findAccountByUserWorks() throws Exception {
        assertEquals(pertti.getId(), accountService.findAccountByUser(pertti.getUsername()).getId());
        assertEquals(martti.getId(), accountService.findAccountByUser(martti.getUsername()).getId());

    }

    @Test
    public void createProfileAndAssignToUserWorks() throws Exception {
        Profile p = accountService.findAccountByUser(pertti.getUsername()).getProfile().get(0);
        assertEquals(p.getId(), perttiProfile1.getId());

        p = accountService.findAccountByUser(martti.getUsername()).getProfile().get(0);
        assertEquals(p.getId(), marttiProfile1.getId());
    }

    @Test
    public void findManyQuestionsWorks() throws Exception {
        List<Long> questionIds = Arrays.asList(question1.getId(), question2.getId(), question3.getId(), question4.getId());
        questionService.findManyQuestions(questionIds);
        assertTrue(questionIds.contains(question1.getId()) && questionIds.contains(question2.getId())
                && questionIds.contains(question3.getId()) && questionIds.contains(question4.getId()));
    }

    @Test
    public void assignQuestionToProfileWorks() throws Exception {
        question4 = questionRepository.findOne(question4.getId());
        perttiProfile1 = profileRepository.findOne(perttiProfile1.getId());
        ProfileQuestion pq = profileQuestionService.assignQuestionToProfile(perttiProfile1.getId(), question4.getId(), question4.getAnswerOptions().get(0).getId());
        pq = profileQuestionRepository.findOne(pq.getId());
        perttiProfile1 = profileRepository.findOne(perttiProfile1.getId());
        question4 = questionRepository.findOne(question4.getId());
        assertEquals(pq.getQuestion().getId(), perttiProfile1.getProfileQuestions().get(0).getQuestion().getId());
        assertEquals(pq.getQuestion().getAnswerOptions().get(0).getId(), perttiProfile1.getProfileQuestions().get(0).getCorrectAnswer().getId());
    }
}
