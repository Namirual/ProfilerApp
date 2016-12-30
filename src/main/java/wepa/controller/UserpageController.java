package wepa.controller;

import java.util.Arrays;
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

    private final int showPerPage = 3;

    @RequestMapping
    public String viewUserpage() {
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

        // Check if the parameter page numbers are valid and correct to a valid one if necessary
        page = checkPage(page, answeredProfiles.size());
        ownPage = checkPage(ownPage, profiles.size());
        
        // Insert the correct page numbers for the links
        List<Integer> pages = getLinkPages(page, answeredProfiles.size());
        int previous = pages.get(0);
        int next = pages.get(1);

        List<Integer> ownPages = getLinkPages(ownPage, profiles.size());
        int previousOwn = ownPages.get(0);
        int nextOwn = ownPages.get(1);

        model.addAttribute("next", next);
        model.addAttribute("current", page);
        model.addAttribute("previous", previous);
        model.addAttribute("nextOwn", nextOwn);
        model.addAttribute("currentOwn", ownPage);
        model.addAttribute("previousOwn", previousOwn);

        model.addAttribute("name", realName);
        model.addAttribute("answerNum", answeredProfiles.size());
        model.addAttribute("score", profilerScore);

        // Pass a sublist of the profile list into the model.
        // The list is either the length of showPerPage or capped by the end of the list.
        model.addAttribute("profiles", profiles.subList(showPerPage * (ownPage - 1), Math.min(profiles.size(), showPerPage * ownPage)));
        model.addAttribute("answered", answeredProfiles.subList(showPerPage * (page - 1), Math.min(answeredProfiles.size(), showPerPage * page)));

        return "userpage";
    }

    private Integer checkPage(int currentPage, int profileSize) {
        if (currentPage < 1) {
            return 1;
        }

        if (profileSize <= showPerPage * (currentPage - 1)) {
            return 1;
        }
        return currentPage;
    }

    private List<Integer> getLinkPages(int currentPage, int profileSize) {
        int previousPage = 0;
        int nextPage = 0;

        if (currentPage > 1) {
            previousPage = currentPage - 1;
        }

        if (profileSize > showPerPage * currentPage) {
            nextPage = currentPage + 1;
        }

        return Arrays.asList(previousPage, nextPage);
    }
}
