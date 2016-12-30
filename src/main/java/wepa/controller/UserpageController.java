package wepa.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
        /*       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountService.findAccountByUser(auth.getName());

        String realName = user.getName();

        List<Profile> answeredProfiles = user.getAnsweredProfiles();

        int profilerScore = answerService.calculateUserProfilerScore(user);

        List<Profile> profiles = user.getProfiles();

        model.addAttribute("name", realName);
        model.addAttribute("answerNum", answeredProfiles.size());
        model.addAttribute("score", profilerScore);
        model.addAttribute("profiles", profiles);
        model.addAttribute("answered", answeredProfiles);*/

        return "redirect:/userpage/1/own/1/";
    }

    @RequestMapping(value = "/{page}/own/{ownPage}")
    public String viewOwnOnUserpage(Model model, @PathVariable int page, @PathVariable int ownPage) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountService.findAccountByUser(auth.getName());

        String realName = user.getName();
        List<Profile> answeredProfiles = user.getAnsweredProfiles();
        int profilerScore = answerService.calculateUserProfilerScore(user);
        List<Profile> profiles = user.getProfiles();

        int showPerPage = 2;

        // Links for passing to pages
        int previous = 0;
        int next = 0;
        int previousOwn = 0;
        int nextOwn = 0;

        // Check if the parameters are valid and correct them if necessary
        
        if (page < 1) {
            page = 1;
        }

        if (ownPage < 1) {
            ownPage = 1;
        }

        if (profiles.size() <= showPerPage * (ownPage-1)) {
            ownPage = 1;
        }

        if (answeredProfiles.size() <= showPerPage * (page-1)) {
            page = 1;
        }

        // Insert the correct numbers for the links
        
        if (page > 1) {
            previous = page - 1;
        }

        if (answeredProfiles.size() > showPerPage * page) {
            next = page + 1;
        }

        if (ownPage > 1) {
            previousOwn = ownPage - 1;
        }

        if (profiles.size() > showPerPage * ownPage) {
            nextOwn = ownPage + 1;
        }

        model.addAttribute("next", next);
        model.addAttribute("current", page);
        model.addAttribute("previous", previous);
        model.addAttribute("nextOwn", nextOwn);
        model.addAttribute("currentOwn", ownPage);
        model.addAttribute("previousOwn", previousOwn);

        model.addAttribute("name", realName);
        model.addAttribute("answerNum", answeredProfiles.size());
        model.addAttribute("score", profilerScore);

        model.addAttribute("profiles", profiles.subList(showPerPage * (ownPage - 1), profiles.size()));

        if (profiles.size() > showPerPage * ownPage) {
            model.addAttribute("profiles", profiles.subList(showPerPage * (ownPage - 1), showPerPage * (ownPage)));
        } else {
            model.addAttribute("profiles", profiles.subList(showPerPage * (ownPage - 1), profiles.size()));
        }

        if (answeredProfiles.size() > showPerPage * page) {
            model.addAttribute("answered", answeredProfiles.subList(showPerPage * (page - 1), showPerPage * (page)));
        } else {
            model.addAttribute("answered", answeredProfiles.subList(showPerPage * (page - 1), answeredProfiles.size()));
        }

        return "userpage";
    }
}
