/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.service;

/**
 *
 * @author lmantyla
 */
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wepa.domain.Account;
import wepa.domain.Answer;
import wepa.domain.Profile;
import wepa.domain.ProfileQuestion;
import wepa.domain.Question;
import wepa.domain.ImageObject;
import wepa.repository.*;
import wepa.service.AccountService;
import wepa.service.ProfileQuestionService;
import wepa.service.ProfileService;
import wepa.service.QuestionService;
import wepa.service.initservice.InitService;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@org.springframework.context.annotation.Profile("test")

// Class tests object creation using the methods of the InitService class.
public class InitIntegrationTest {

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
    @Autowired
    private ImageObjectRepository imageObjectRepository;

    @Autowired
    private InitService initService;

    Question question1;
    Question question2;
    Question question3;
    Question question4;

    Account account1;
    Account account2;

    Profile profile1;
    Profile profile2;

    @Before
    public void setUp() throws Exception {
        question1 = questionService.createQuestion("How old is this person?", Arrays.asList("Still a child", "20-25", "25-30", "30-35", "35-40", "old as fuck"));
        question2 = questionService.createQuestion("How tall is this person?", Arrays.asList("Midget", "140-150", "150-160", "160-170", "170-180", "180-190", "190-200", "HUGE"));
        question3 = questionService.createQuestion("Does this person look like a dog or a cat person?", Arrays.asList("Neither", "Dog", "Cat", "Both"));
        question4 = questionService.createQuestion("Does this person look like Bitch?", Arrays.asList("No", "Yes", "What?"));

        account1 = initService.createAccount("test", "test", "test", "test@m", false);
        account2 = initService.createAccount("test2", "test2", "test2", "test2@m", false);

        profile1 = initService.createTestProfile(account1, Arrays.asList(question1, question2), "public/img/gilga.jpg");
        profile2 = initService.createTestProfile(account2, Arrays.asList(question1, question4), "public/img/assur.jpg");
    }

    @After
    public void tearDown() throws Exception {
        /*account1.getAnsweredProfiles().clear();
        account2.getAnsweredProfiles().clear();
        accountRepository.save(account1);
        accountRepository.save(account2);*/

//        answerRepository.deleteAll();
//        profileQuestionRepository.deleteAll();
//        answerOptionRepository.deleteAll();
//        questionRepository.deleteAll();
//        profileRepository.deleteAll();
//        accountRepository.deleteAll();
//        imageObjectRepository.deleteAll();
        profileRepository.deleteAll();
        answerRepository.deleteAll();
        profileQuestionRepository.deleteAll();
        answerOptionRepository.deleteAll();
        questionRepository.deleteAll();
        accountRepository.deleteAll();
        imageObjectRepository.deleteAll();


    }
/*
    @Test
    public void canCreateProfileWithPic() {
        Profile profile = initService.createTestProfile(account1, Arrays.asList(question1, question2), "public/img/alabaster.jpg");
        ImageObject image1 = imageObjectRepository.findOne(profile.getProfilePicId());
        ImageObject image2 = imageObjectRepository.findOne(profile.getThumbnailId());

        assertEquals(6, imageObjectRepository.findAll().size());
    }
*/
    @Test
    public void canAnswerProfiles() {
        initService.createTestAnswers(account1, profile2, Arrays.asList(5, 0));
        assertEquals(2, answerRepository.findAll().size());
    }

    @Test
    public void canSaveAnsweredProfiles() {
        initService.createTestAnswers(account1, profile2, Arrays.asList(5, 0));
        assertEquals(1, account1.getAnsweredProfiles().size());
    }

    @Test
    public void savedAnswersMatchSelectedAnswers() {
        initService.createTestAnswers(account1, profile2, Arrays.asList(5, 0));
        Answer answer1 = answerRepository.findByProfileQuestionAndAccount(profile2.getProfileQuestions().get(0), account1);
        Answer answer2 = answerRepository.findByProfileQuestionAndAccount(profile2.getProfileQuestions().get(1), account1);

        assertEquals(answer1.getAnswer(), profile2.getProfileQuestions().get(0).getQuestion().getAnswerOptions().get(5));
        assertEquals(answer2.getAnswer(), profile2.getProfileQuestions().get(1).getQuestion().getAnswerOptions().get(0));
    }

    @Test
    public void correctAnswersCanBeCompared() {
        initService.createTestAnswers(account1, profile2, Arrays.asList(5, 0));
        Answer answer1 = answerRepository.findByProfileQuestionAndAccount(profile2.getProfileQuestions().get(0), account1);
        Answer answer2 = answerRepository.findByProfileQuestionAndAccount(profile2.getProfileQuestions().get(1), account1);

        assertEquals(answer1.getAnswer(), profile2.getProfileQuestions().get(0).getCorrectAnswer());
        assertNotEquals(answer2.getAnswer(), profile2.getProfileQuestions().get(1).getCorrectAnswer());

    }

}
