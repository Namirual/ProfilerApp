package wepa.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import wepa.domain.Account;
import wepa.repository.AccountRepository;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("default")
public class RepositoryIntegrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void canSaveAccount() {

    }

}