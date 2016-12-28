package wepa.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import wepa.domain.Account;
import wepa.domain.Profile;
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
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProfileRepository profileRepository;


    @Before
    public void setUp() throws Exception {
        pertti = new Account();
        pertti.setName("Pertti");
        pertti.setEmail("pertti@foomail.com");
        pertti.setUsername("perrti");
        pertti.setAuthorities(Arrays.asList("ADMIN"));
        pertti.setPassword("pertinSalasana");
        accountRepository.save(pertti);
        martti = new Account();
        martti.setName("Martti");
        martti.setEmail("martti@foomail.com");
        martti.setUsername("marrti");
        martti.setAuthorities(Arrays.asList("USER"));
        martti.setPassword("martinSalasana");
        accountRepository.save(martti);

        profile1 = new wepa.domain.Profile();
        profile1.setActive(true);
        profile1.setOwnerAccount(pertti);
        profileRepository.save(profile1);

        profile2 = new wepa.domain.Profile();
        profile2.setActive(false);
        profile2.setOwnerAccount(pertti);
        profileRepository.save(profile2);

        profile3 = new wepa.domain.Profile();
        profile3.setActive(true);
        profile3.setOwnerAccount(martti);
        profileRepository.save(profile3);

    }

    @After
    public void tearDown() throws Exception {
        profileRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void canSaveAccount() {
        assertNotEquals(0, accountRepository.findAll().size());
    }

    @Test
    public void canSetProfileOwnerAccount() throws Exception {
        List<Profile> profiles = profileRepository.findByOwnerAccount(pertti);
        assertEquals(2, profiles.size());
        assertEquals(profiles.get(0).getOwnerAccount().getUsername(), pertti.getUsername());
        assertEquals(profiles.get(1).getOwnerAccount().getUsername(), pertti.getUsername());

    }
}