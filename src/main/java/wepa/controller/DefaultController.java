package wepa.controller;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.domain.Account;
import wepa.repository.AccountRepository;

// Default controller handles logging in, out and creating new accounts.
@Controller
@RequestMapping("*")
public class DefaultController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute
    private Account getAccount() {
        return new Account();
    }
    
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String viewLogin(Model model) {

        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET, params = "error")
    public String viewLoginError(Model model) {
        String errorMessage = "Incorrect username or password.";
        model.addAttribute("error", errorMessage);

        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET, params = "logout")
    public String viewLogoutMessage(Model model) {
        String logoutMessage = "You have logged out.";
        model.addAttribute("logout", logoutMessage);

        return "login";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String viewSignup(Model model) {
        return "signup";
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String newAccount(@Valid @ModelAttribute Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }
        account.setAuthorities(Arrays.asList("USER"));
        // encode the user's password
        String password = account.getPassword();
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
        //System.out.println("GG user added: " + account.getEmail());
        return "redirect:/";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goToIndex() {
        return "redirect:/index";
    }

}
