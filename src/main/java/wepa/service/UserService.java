package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wepa.domain.Account;
import wepa.repository.AccountRepository;



import java.util.Arrays;
import java.util.List;


@Service
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository userDetailsRepository;

    public Account createUser(String username, String password, List<String> authorities) {
        Account user = new Account();
        if (userDetailsRepository.findByUsername(username) == null) {

            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setAuthorities(authorities);
            user = userDetailsRepository.save(user);
        }
        return user;
    }
    public Account createDefaultUserForTesting() {
        Account user = new Account();
        if (userDetailsRepository.findByUsername("maxwell") == null) {

            user.setUsername("maxwell");
            user.setPassword("$2a$10$HOenmXvSwgDYdJLt5mvYoelgKWRem9UcFf.yTUOjal7aoOsvf2SWi");
            user.setAuthorities(Arrays.asList("USER"));
            user = userDetailsRepository.save(user);
        }
        return user;
    }
    public Account createDefaultAdminForTesting() {
        Account admin = new Account();
        if (userDetailsRepository.findByUsername("reino") == null) {

            admin.setUsername("reino");
            admin.setPassword("$2a$10$3fT4VDT7t7N4bUtdajXa4ekC6NzAj9EO/08u920sR66wDFonlV.au");
            admin.setAuthorities(Arrays.asList("ADMIN"));
            admin = userDetailsRepository.save(admin);
        }
        return admin;
    }

    public Account findUser(Account user) {
        return userDetailsRepository.findOne(user.getId());
    }
}