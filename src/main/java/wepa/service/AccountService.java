package wepa.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Account;
import wepa.domain.Profile;
import wepa.repository.AccountRepository;

// This class handles account creation

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
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
        account.addAnsweredProfile(profile);
        account = accountRepository.save(account);
        return account;
    }
    
}
