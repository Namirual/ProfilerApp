package wepa.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Account;
import wepa.domain.Answer;
import wepa.domain.ProfileQuestion;
import wepa.service.AnswerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("default")
public class AnswerRepositoryTest {
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

    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    @Transactional
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

        answer1 = answerRepository.save(new Answer());
        answer2 = answerRepository.save(new Answer());
        answer3 = answerRepository.save(new Answer());
        answer4 = answerRepository.save(new Answer());
        answer5 = answerRepository.save(new Answer());

        profileQuestion1 = profileQuestionRepository.save(new ProfileQuestion());
        profileQuestion2 = profileQuestionRepository.save(new ProfileQuestion());
        profileQuestion3 = profileQuestionRepository.save(new ProfileQuestion());
        profileQuestion4 = profileQuestionRepository.save(new ProfileQuestion());
        profileQuestion5 = profileQuestionRepository.save(new ProfileQuestion());

        answer1.setProfileQuestion(profileQuestion1);
        answer2.setProfileQuestion(profileQuestion2);
        answer3.setProfileQuestion(profileQuestion3);
        answer4.setProfileQuestion(profileQuestion4);
        answer5.setProfileQuestion(profileQuestion5);

        answer1.setAnswerer(pertti);
        answer2.setAnswerer(pertti);
        answer3.setAnswerer(pertti);
        answer4.setAnswerer(martti);
        answer5.setAnswerer(martti);

        answer1 = answerRepository.save(answer1);
        answer2 = answerRepository.save(answer2);
        answer3 = answerRepository.save(answer3);
        answer4 = answerRepository.save(answer4);
        answer5 = answerRepository.save(answer5);

    }

    @After
    public void tearDown() throws Exception {
        answerRepository.deleteAll();
        profileQuestionRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void findByProfileQuestionListFindsAnyAnswers() throws Exception {
        List<ProfileQuestion> profileQuestions = Arrays.asList(profileQuestion2, profileQuestion4, profileQuestion5);
        List<Answer> answers = answerRepository.findByProfileQuestionIn(profileQuestions);
        assertNotNull(answers);
        assertNotEquals(0, answers.size());
        assertEquals(3, answers.size());
    }

    @Test
    public void findByProfileQuestionListFindsCorrectAnswers() throws Exception {
        List<ProfileQuestion> profileQuestions = Arrays.asList(profileQuestion2, profileQuestion4, profileQuestion5);
        List<Answer> answers = answerRepository.findByProfileQuestionIn(profileQuestions);
        List<Long> answerIds = new ArrayList<>();
        for (Answer a : answers) {
            answerIds.add(a.getId());
        }
        assertTrue(answerIds.contains(answer2.getId()) &&
                answerIds.contains(answer4.getId()) && answerIds.contains(answer5.getId()));
        assertFalse(answerIds.contains(answer2.getId()) &&
                answerIds.contains(answer1.getId()) && answerIds.contains(answer3.getId()));
    }

    @Test
    public void findByProfileQuestionListAndAccountFindsAnyAnswers() throws Exception {
        List<ProfileQuestion> profileQuestions = Arrays.asList(profileQuestion2, profileQuestion4, profileQuestion5);
        List<Answer> answers = answerRepository.findByProfileQuestionInAndAccount(profileQuestions, martti);
        assertNotNull(answers);
        assertNotEquals(0, answers.size());
        assertEquals(2, answers.size());
    }


    @Test
    public void findByProfileQuestionListAndAccountFindsCorrectAnswers() throws Exception {
        List<ProfileQuestion> profileQuestions = Arrays.asList(profileQuestion2, profileQuestion4, profileQuestion5);
        List<Answer> answers = answerRepository.findByProfileQuestionInAndAccount(profileQuestions, martti);
        List<Long> answerIds = new ArrayList<>();
        for (Answer a : answers) {
            answerIds.add(a.getId());
        }
        assertTrue(answerIds.contains(answer4.getId()) && answerIds.contains(answer5.getId()));
    }

    @Test
    public void findBySingleProfileQuestion() throws Exception {
        assertEquals(answer1.getId(), answerRepository.findByProfileQuestion(profileQuestion1).get(0).getId());
        assertNotEquals(answer2.getId(), answerRepository.findByProfileQuestion(profileQuestion1).get(0).getId());
    }

    @Test
    public void findBySingleProfileQuestionAndSingleAccount() throws Exception {

    }

}