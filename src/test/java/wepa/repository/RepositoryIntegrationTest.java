package wepa.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.*;
import wepa.service.AnswerService;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@org.springframework.context.annotation.Profile("test")
public class RepositoryIntegrationTest {
    private Account pertti;
    private Account martti;

    private Profile profile1;
    private Profile profile2;
    private Profile profile3;

    private ProfileQuestion profileQuestion1;
    private ProfileQuestion profileQuestion2;
    private ProfileQuestion profileQuestion3;
    private ProfileQuestion profileQuestion4;

    private Question question1;
    private Question question2;

    private AnswerOption answerOption1;
    private AnswerOption answerOption2;
    private AnswerOption answerOption3;
    private AnswerOption answerOption4;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerOptionRepository answerOptionRepository;


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

        profile1 = profileRepository.save(new Profile());
        profile1.setActive(true);
        profile1.setOwnerAccount(pertti);
        profile1 = profileRepository.save(profile1);

        profile2 = profileRepository.save(new Profile());
        profile2.setActive(false);
        profile2.setOwnerAccount(pertti);
        profile2 = profileRepository.save(profile2);

        profile3 = profileRepository.save(new Profile());
        profile3.setActive(true);
        profile3.setOwnerAccount(martti);
        profile3 = profileRepository.save(profile3);

        profileQuestion1 = profileQuestionRepository.save(new ProfileQuestion());
        profileQuestion2 = profileQuestionRepository.save(new ProfileQuestion());
        profileQuestion3 = profileQuestionRepository.save(new ProfileQuestion());
        profileQuestion4 = profileQuestionRepository.save(new ProfileQuestion());

        profileQuestion1.setProfile(profile1);
        profileQuestion2.setProfile(profile2);
        profileQuestion3.setProfile(profile2);
        profileQuestion4.setProfile(profile3);

        profile1.setProfileQuestions(Arrays.asList(profileQuestion1));
        profile2.setProfileQuestions(Arrays.asList(profileQuestion2, profileQuestion3));
        profile3.setProfileQuestions(Arrays.asList(profileQuestion4));

        profileQuestion1 = profileQuestionRepository.save(profileQuestion1);
        profileQuestion2 = profileQuestionRepository.save(profileQuestion2);
        profileQuestion3 = profileQuestionRepository.save(profileQuestion3);
        profileQuestion4 = profileQuestionRepository.save(profileQuestion4);

        question1 = questionRepository.save(new Question("Onko kivaa?"));
        question2 = questionRepository.save(new Question("Mitä kuuluu?"));

        answerOption1 = answerOptionRepository.save(new AnswerOption());
        answerOption2 = answerOptionRepository.save(new AnswerOption());
        answerOption3 = answerOptionRepository.save(new AnswerOption());
        answerOption4 = answerOptionRepository.save(new AnswerOption());

        answerOption1.setAnswerText("Kyllä");
        answerOption2.setAnswerText("Ei");
        answerOption3.setAnswerText("Hyvää");
        answerOption4.setAnswerText("Huonoa");

        answerOption1.setQuestion(question1);
        answerOption2.setQuestion(question1);
        answerOption3.setQuestion(question2);
        answerOption4.setQuestion(question2);

        question1.setAnswerOptions(Arrays.asList(answerOption1, answerOption2));
        question2.setAnswerOptions(Arrays.asList(answerOption3, answerOption4));

        answerOption1 = answerOptionRepository.save(answerOption1);
        answerOption2 = answerOptionRepository.save(answerOption2);
        answerOption3 = answerOptionRepository.save(answerOption3);
        answerOption4 = answerOptionRepository.save(answerOption4);

        question1 = questionRepository.save(question1);
        question2 = questionRepository.save(question2);

        profileQuestion1.setQuestion(question1);
        profileQuestion2.setQuestion(question2);
        profileQuestion3.setQuestion(question1);
        profileQuestion4.setQuestion(question2);

        profileQuestion1.setCorrectAnswer(answerOption1);
        profileQuestion2.setCorrectAnswer(answerOption3);
        profileQuestion3.setCorrectAnswer(answerOption2);
        profileQuestion4.setCorrectAnswer(answerOption4);

        profileQuestionRepository.save(profileQuestion1);
        profileQuestionRepository.save(profileQuestion2);
        profileQuestionRepository.save(profileQuestion3);
        profileQuestionRepository.save(profileQuestion4);


    }
    @Transactional
    @After
    public void tearDown() throws Exception {

        profileRepository.deleteAll();
        profileQuestionRepository.deleteAll();
        answerOptionRepository.deleteAll();
        questionRepository.deleteAll();
        profileRepository.deleteAll();
        accountRepository.deleteAll();


    }

    @Test
    public void canSaveAccount() {
        assertEquals(2, accountRepository.findAll().size());
    }

    @Test
    public void canSetProfileOwnerAccount() throws Exception {
        List<Profile> profiles = profileRepository.findByOwnerAccount(pertti);
        assertEquals(2, profiles.size());
        assertEquals(profiles.get(0).getOwnerAccount().getUsername(), pertti.getUsername());
        assertEquals(profiles.get(1).getOwnerAccount().getUsername(), pertti.getUsername());

    }

    @Test
    public void profilesHaveProfileQuestionsAfterSave() throws Exception {
        List<ProfileQuestion> profileQuestions = profile1.getProfileQuestions();
        assertEquals(1, profileQuestions.size());

        profileQuestions = profile2.getProfileQuestions();
        assertEquals(2, profileQuestions.size());

        profileQuestions = profile3.getProfileQuestions();
        assertEquals(1, profileQuestions.size());
    }

    @Test
    public void profilesHaveCorrectProfileQuestionsAfterSave() throws Exception {
        List<ProfileQuestion> profileQuestions = profile1.getProfileQuestions();
        assertEquals(profileQuestion1.getId(), profileQuestions.get(0).getId());

        profileQuestions = profile2.getProfileQuestions();
        assertEquals(profileQuestion2.getId(), profileQuestions.get(0).getId());
        assertEquals(profileQuestion3.getId(), profileQuestions.get(1).getId());

        profileQuestions = profile3.getProfileQuestions();
        assertEquals(profileQuestion4.getId(), profileQuestions.get(0).getId());
    }

    @Test
    public void profileQuestionsHaveAQuestionAfterSave() throws Exception {
        List<ProfileQuestion> profileQuestions = profileQuestionRepository.findAll();
        for (ProfileQuestion pq : profileQuestions) {
            assertNotNull(pq.getQuestion().getId());
        }
    }

    @Test
    public void profileQuestionsHaveCorrectQuestionsAfterSave() throws Exception {
        ProfileQuestion pq = profileQuestionRepository.findOne(profileQuestion1.getId());
        assertEquals(question1.getContent(), pq.getQuestion().getContent());

        pq = profileQuestionRepository.findOne(profileQuestion2.getId());
        assertEquals(question2.getContent(), pq.getQuestion().getContent());

        pq = profileQuestionRepository.findOne(profileQuestion3.getId());
        assertEquals(question1.getContent(), pq.getQuestion().getContent());

        pq = profileQuestionRepository.findOne(profileQuestion4.getId());
        assertEquals(question2.getContent(), pq.getQuestion().getContent());
    }

    @Test
    public void accountsHaveQuestions() throws Exception {
        Account account = accountRepository.findOne(pertti.getId());
        assertNotNull(account.getProfiles().get(0).getProfileQuestions().get(0).getQuestion().getId());

        account = accountRepository.findOne(martti.getId());
        assertNotNull(account.getProfiles().get(0).getProfileQuestions().get(0).getQuestion().getId());
    }

    @Test
    public void questionsHaveAnswerOptions() throws Exception {
        Question question = questionRepository.findOne(question1.getId());
        assertNotNull(question.getAnswerOptions());
        assertNotEquals(0, question.getAnswerOptions().size());

        question = questionRepository.findOne(question2.getId());
        assertNotNull(question.getAnswerOptions());
        assertNotEquals(0, question.getAnswerOptions().size());
    }

    @Test
    public void answerOptionsHaveQuestions() throws Exception {
        AnswerOption a = answerOptionRepository.findOne(answerOption1.getId());
        assertNotNull(a.getQuestion().getId());

        a = answerOptionRepository.findOne(answerOption2.getId());
        assertNotNull(a.getQuestion().getId());

        a = answerOptionRepository.findOne(answerOption3.getId());
        assertNotNull(a.getQuestion().getId());

        a = answerOptionRepository.findOne(answerOption4.getId());
        assertNotNull(a.getQuestion().getId());

    }

    @Test
    public void profileQuestionHasRightAnswer() throws Exception {
//        profileQuestion1.setCorrectAnswer(answerOption1);
//        profileQuestion2.setCorrectAnswer(answerOption3);
//        profileQuestion3.setCorrectAnswer(answerOption2);
//        profileQuestion4.setCorrectAnswer(answerOption4);

        ProfileQuestion pq = profileQuestionRepository.findOne(profileQuestion1.getId());
        assertEquals(pq.getCorrectAnswer().getId(), answerOption1.getId());
        pq = profileQuestionRepository.findOne(profileQuestion2.getId());
        assertEquals(pq.getCorrectAnswer().getId(), answerOption3.getId());
        pq = profileQuestionRepository.findOne(profileQuestion3.getId());
        assertEquals(pq.getCorrectAnswer().getId(), answerOption2.getId());
        pq = profileQuestionRepository.findOne(profileQuestion4.getId());
        assertEquals(pq.getCorrectAnswer().getId(), answerOption4.getId());
    }
}