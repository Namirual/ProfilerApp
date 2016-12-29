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
import wepa.service.AnswerService;
import wepa.service.ProfileService;

@Controller
@RequestMapping("/userpage")
public class UserpageController {
    
    @Autowired
    private ProfileService profileService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private AnswerService answerService;
    
    @RequestMapping
    public String viewUserpage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountService.findAccountByUser(auth.getName());
        
        List<Profile> answeredProfiles = user.getAnsweredProfiles();
        
        int profilerScore = answerService.calculateUserProfilerScore(user);

        List<Profile> profiles = user.getProfiles();
        
        model.addAttribute("answerNum", answeredProfiles.size());
        model.addAttribute("score", profilerScore);
        model.addAttribute("profiles", profiles);
        model.addAttribute("answered", answeredProfiles);
        
        return "userpage";
    }
    
}
