package wepa.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Account;
import wepa.domain.Answer;
import wepa.domain.Profile;
import wepa.repository.AccountRepository;
import wepa.repository.AnswerRepository;
import wepa.repository.ProfileRepository;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

// This class handles account creation

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;
    @Autowired
    private HttpSession session;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerRepository answerRepository;
    @PostConstruct
    public void initFooBar() {
        Account fooBar;
        if (accountRepository.findByUsername("FooBar") == null) {
            Account fooAccount = new Account();
            fooAccount.setUsername("FooBar");
            fooAccount.setEmail("foobar@foomail.com");
            fooAccount.setName("FooBar");
            fooAccount.setPassword("$2a$10$HOenmXvSwgDYdJLt5mvYoelgKWRem9UcFf.yTUOjal7aoOsvf2SWi");

            fooBar = accountRepository.save(fooAccount);
        } else {
            fooBar = accountRepository.findByUsername("FooBar");
        }
    }
    @Transactional
    public Account createAccount(Account account) {
        // Only if an account exists should it have an id, so check if the account
        // exists by checking if it has an id.
        if (account.getId() != null) {
            return accountRepository.findOne(account.getId());
        }
        return accountRepository.save(account);
    }

    public List<Account> getAllUsers() {
        return accountRepository.findAll();
    }

    public Account getUserById(String id) {
        return accountRepository.findOne(id);
    }

    public Account findAccountByUser(String username) {
        return accountRepository.findByUsername(username);
    }

    @Transactional
    public Account addAnswerToAccount(Account account, Profile profile) {
        profile = profileRepository.findOne(profile.getId());
        profile.addAnsweringAccount(account);
        profileRepository.save(profile);
        account.addAnsweredProfile(profile);
        account = accountRepository.save(account);
        return account;
    }

    @Transactional
    public void deleteAccount() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = findAccountByUser(auth.getName());
        List<Profile> usersProfiles = user.getProfiles();
        Account fooBar = accountRepository.findByUsername("FooBar");
        if (accountRepository.findByUsername("FooBar") == null) {
            Account fooAccount = new Account();
            fooAccount.setUsername("FooBar");
            fooAccount.setEmail("foobar@foomail.com");
            fooAccount.setName("FooBar");
            fooAccount.setPassword("$2a$10$HOenmXvSwgDYdJLt5mvYoelgKWRem9UcFf.yTUOjal7aoOsvf2SWi");

            fooBar = accountRepository.save(fooAccount);
        } else {
            fooBar = accountRepository.findByUsername("FooBar");
        }

        for (Profile profile : usersProfiles) {
            System.out.println("Profile Id " + profile.getId());
            profileService.deleteProfile(profile);
        }
        List<Answer> usersAnswers = answerRepository.findByAccount(user);
        for (Answer answer : usersAnswers) {
            answer.setAnswerer(fooBar);
            answerRepository.save(answer);
        }
        List<Profile> allProfiles = profileRepository.findAll();
        for (Profile p:allProfiles) {
            List<Account> answeringAccounts = p.getAnsweringAccounts();
            for (int i = 0; i <answeringAccounts.size() ; i++) {
                if(answeringAccounts.get(i).getId() == user.getId()) {
                    answeringAccounts.set(i, fooBar);
                    profileRepository.save(p);
                }
            }
        }
        accountRepository.delete(user);
        auth.setAuthenticated(false);
        session.invalidate();
    }

    @Transactional
    public void adminDeleteAccount(Account user) {
        user = accountRepository.findOne(user.getId());
        List<Profile> usersProfiles = user.getProfiles();
        Account fooBar = accountRepository.findByUsername("FooBar");
        if (accountRepository.findByUsername("FooBar") == null) {
            Account fooAccount = new Account();
            fooAccount.setUsername("FooBar");
            fooAccount.setEmail("foobar@foomail.com");
            fooAccount.setName("FooBar");
            fooAccount.setPassword("$2a$10$HOenmXvSwgDYdJLt5mvYoelgKWRem9UcFf.yTUOjal7aoOsvf2SWi");

            fooBar = accountRepository.save(fooAccount);
        } else {
            fooBar = accountRepository.findByUsername("FooBar");
        }

        for (Profile profile : usersProfiles) {
            System.out.println("Profile Id " + profile.getId());
            profileService.deleteProfile(profile);
        }
        List<Answer> usersAnswers = answerRepository.findByAccount(user);
        for (Answer answer : usersAnswers) {
            answer.setAnswerer(fooBar);
            answerRepository.save(answer);
        }
        List<Profile> allProfiles = profileRepository.findAll();
        for (Profile p:allProfiles) {
            List<Account> answeringAccounts = p.getAnsweringAccounts();
            for (int i = 0; i <answeringAccounts.size() ; i++) {
                if(answeringAccounts.get(i).getId() == user.getId()) {
                    answeringAccounts.set(i, fooBar);
                    profileRepository.save(p);
                }
            }
        }
        accountRepository.delete(user);
    }

}
