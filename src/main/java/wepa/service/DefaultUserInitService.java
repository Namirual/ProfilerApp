package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wepa.domain.Account;
import wepa.repository.AccountRepository;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Profile("default")
@Service
public class DefaultUserInitService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository userDetailsRepository;
    @PostConstruct
    public void initDefaultUsers() {

        Account user = new Account();
        user.setUsername("reino");
        user.setPassword(passwordEncoder.encode("huopatossu"));
        user.setAuthorities(Arrays.asList("ADMIN"));
        user = userDetailsRepository.save(user);

        Account user2 = new Account();
        user2.setUsername("maxwell");
        user2.setPassword(passwordEncoder.encode("smart"));
        user2.setAuthorities(Arrays.asList("USER"));
        user2 = userDetailsRepository.save(user2);
        System.out.println(user2.getPassword());
    }
}