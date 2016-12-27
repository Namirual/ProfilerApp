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
import wepa.service.ProfileService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@org.springframework.context.annotation.Profile("default")
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AccountRepository accountRepository;
    private Account pertti;
    private Account martti;
    private Profile profile1;
    private Profile profile2;
    private Profile profile3;

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

        profile1 = new Profile();
        profile1.setActive(true);
        profile1.setOwnerAccount(pertti);
        profileRepository.save(profile1);

        profile2 = new Profile();
        profile2.setActive(false);
        profile2.setOwnerAccount(pertti);
        profileRepository.save(profile2);

        profile3 = new Profile();
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
    public void findByOwnerAccount() throws Exception {
        List<Profile> profiles = profileRepository.findByOwnerAccount(pertti);
        assertEquals(2, profiles.size());
        assertEquals(profiles.get(0).getOwnerAccount().getUsername(), pertti.getUsername());
        assertEquals(profiles.get(1).getOwnerAccount().getUsername(), pertti.getUsername());
    }


    @Test
    public void findFirstByOrderByCreationTimeInMillisDesc() throws Exception {

    }

    @Test
    public void findFirstByCreationTimeInMillisGreaterThan() throws Exception {

    }

    @Test
    public void findFirstByCreationTimeInMillisLessThan() throws Exception {

    }

    @Test
    public void profilesHaveUniqueCreationTimes() throws Exception {
        List<Profile> profiles = profileRepository.findAll();
        assertNotEquals(profiles.get(0), profiles.get(1));
        System.out.println("profile1 CT " + profile1.getCreationTimeInMillis());
        System.out.println("profile2 CT " + profile2.getCreationTimeInMillis());
        System.out.println("profile3 CT " + profile3.getCreationTimeInMillis());
        assertNotEquals(profiles.get(1), profiles.get(2));
        assertNotEquals(profiles.get(0), profiles.get(2));

    }
}