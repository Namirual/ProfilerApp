package wepa.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import wepa.domain.Account;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("default")
public class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;
    private Account pertti;
    private Account martti;


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
    }

    @After
    public void tearDown() throws Exception {
        accountRepository.deleteAll();

    }

    @Test
    public void findByUsername() throws Exception {
        Account account = accountRepository.findByUsername(pertti.getUsername());
        String expected = pertti.getPassword();
        String actual = account.getPassword();
        assertEquals(expected, actual);

        account = accountRepository.findByUsername(martti.getUsername());
        expected = martti.getPassword();
        actual = account.getPassword();
        assertEquals(expected, actual);
    }

    @Test
    public void findByEmail() throws Exception {
        Account account = accountRepository.findByEmail(pertti.getEmail());
        String expected = pertti.getPassword();
        String actual = account.getPassword();
        assertEquals("Expected " +
                        expected + " but found " + actual,
                expected, actual);

        account = accountRepository.findByEmail(martti.getEmail());
        expected = martti.getPassword();
        actual = account.getPassword();
        assertEquals(expected, actual);
    }

    @Test
    public void findFirstByOrderByCreationTimeInMillisDesc() throws Exception {
        Account account = accountRepository.findFirstByOrderByCreationTimeInMillisDesc();
        Long expected = martti.getCreationTimeInMillis();
        Long actual = account.getCreationTimeInMillis();
        assertEquals(expected, actual);
    }

    @Test
    public void findFirstByCreationTimeInMillisGreaterThanEqual() throws Exception {

    }

    @Test
    public void findFirstByCreationTimeInMillisLessThanEqual() throws Exception {

    }

}