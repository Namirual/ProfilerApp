package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wepa.domain.Account;
import wepa.domain.Profile;
import wepa.service.AccountService;
import wepa.service.ProfileService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProfileService profileService;

    @RequestMapping(method = RequestMethod.GET)
    public String showUsers(Model model) {
        model.addAttribute("accounts", accountService.getAllUsers());
        model.addAttribute("profiles", profileService.getAllProfiles());
        return "admin";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String showUserDetails(@PathVariable String id, Model model) {
        Account account = accountService.getUserById(id);
        model.addAttribute("account", account);
        model.addAttribute("profiles", profileService.findProfilesByAccount(account));
        return "userDetailsForAdmin";
    }

    @RequestMapping(value = "/delete/profile/{id}", method = RequestMethod.POST)
    public String deleteProfile(@PathVariable Long id) {
        Profile profile = profileService.findOneById(id);
        profileService.deleteProfile(profile);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete/account/{id}", method = RequestMethod.POST)
    public String deleteAccount(@PathVariable String id) {
        Account account = accountService.getUserById(id);
        accountService.adminDeleteAccount(account);
        return "redirect:/admin";
    }
}