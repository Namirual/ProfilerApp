/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.service;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import wepa.domain.Account;
import wepa.repository.AccountRepository;

/**
 *
 * @author lmantyla
 */
@Service
public class InitService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute
    private Account getAccount() {
        return new Account();
    }

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ProfileQuestionService profileQuestionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ImageObjectService imageService;

    // Testausta varten, otetaan pois ennen kuin tämä valmistuu
    @PostConstruct
    private void initAccount() {

        createAccount("Gilgamesh", "user", "user", "gilgamesh@sumer.com", false);
        createAccount("Assurnasirpal", "user2", "user2", "assurnaspl@ashur.com", false);
        createAccount("Minos", "user3", "user3", "minos@crete.com", false);
        createAccount("Hippo", "user4", "user4", "hippo@nile.com", false);
        createAccount("Inanna", "admin", "admin", "ishtar@arbela.com", true);
    }

    @PostConstruct
    private void initQuestions() {
        questionService.createQuestion("How old is this person?", Arrays.asList("Still a child", "20-25", "25-30", "30-35", "35-40", "old as fuck"));
        questionService.createQuestion("How tall is this person?", Arrays.asList("Midget", "140-150", "150-160", "160-170", "170-180", "180-190", "190-200", "HUGE"));
        questionService.createQuestion("Does this person look like a dog or a cat person?", Arrays.asList("Neither", "Dog", "Cat", "Both"));
        questionService.createQuestion("Does this person look like Bitch?", Arrays.asList("No", "Yes", "What?"));
    }
    
    @PostConstruct
    private void initProfiles() {
        
    }

    private void createAccount(String name, String username, String password, String email, boolean admin) {
        Account account = new Account();
        account.setName(name);
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setEmail(email);
        if (admin) {
            account.setAuthorities(Arrays.asList("ADMIN"));
        } else {
            account.setAuthorities(Arrays.asList("USER"));
        }
        accountRepository.save(account);
    }
}
