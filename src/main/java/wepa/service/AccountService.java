package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.Account;
import wepa.repository.AccountRepository;

// This class handles account creation

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Account createAccount(Account account) {
        // Only if an account exists should it have an id, so check if the account
        // exists by checking if it has an id.
        if (account.getId() != null) {
            return accountRepository.findOne(account.getId());
        }
        return accountRepository.save(account);
    }
    
    public Account findAccountByUser(String username) {
        return accountRepository.findByUsername(username);
    }
    
}
