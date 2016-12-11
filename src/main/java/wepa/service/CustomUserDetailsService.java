package wepa.service;

import wepa.domain.Account;
import wepa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        List<SimpleGrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (String authority : account.getAuthorities()) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(authority));
//            System.out.println("Granting user " + account.getUsername() + " authority: " + authority);
        }
//        System.out.println("Granted authorities: ");
//        for (SimpleGrantedAuthority ga: grantedAuthorityList) {
//            System.out.println(ga.getAuthority());
//        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                grantedAuthorityList);
    }
}