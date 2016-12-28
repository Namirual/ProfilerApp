package wepa.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Account;
import wepa.domain.Profile;
import wepa.domain.ProfileQuestion;
import wepa.domain.Question;
import wepa.service.AnswerService;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@org.springframework.context.annotation.Profile("default")
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

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;
    @Autowired
    private AnswerService answerService;


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

        profileQuestionRepository.save(profileQuestion1);
        profileQuestionRepository.save(profileQuestion2);
        profileQuestionRepository.save(profileQuestion3);
        profileQuestionRepository.save(profileQuestion4);

    }

    @After
    public void tearDown() throws Exception {
        profileQuestionRepository.deleteAll();
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


}