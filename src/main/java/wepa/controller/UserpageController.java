package wepa.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import wepa.domain.Account;
import wepa.domain.Profile;
import wepa.service.AccountService;
import wepa.service.ProfileService;

@Controller
@RequestMapping("/userpage")
public class UserpageController {
    
    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private AccountService accountService;
    
    @RequestMapping
    public String viewUserpage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountService.findAccountByUser(auth.getName());
        List<Profile> profiles = profileService.findProfilesByAccount(user);
        model.addAttribute("profiles", profiles);
        return "userpage";
    }
    
}
