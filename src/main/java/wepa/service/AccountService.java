package wepa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Account;
import wepa.domain.Profile;
import wepa.repository.AccountRepository;
import wepa.repository.ProfileRepository;

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
    public boolean deleteAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = findAccountByUser(auth.getName());
        List<Profile> usersProfiles = user.getProfiles();
        for (Profile profile:usersProfiles) {
            profileService.deleteProfile(profile);
        }
        accountRepository.delete(user);
        auth.setAuthenticated(false);
        session.invalidate();
        return true;
    }

}
