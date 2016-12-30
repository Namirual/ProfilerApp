package wepa.controller;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wepa.domain.Account;
import wepa.domain.Profile;
import wepa.service.AccountService;
import wepa.service.ProfileService;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AccountService accountService;

    /*@RequestMapping(method = RequestMethod.GET)
    public String getIndex() {

        return "index";
    }*/

    @RequestMapping(method = RequestMethod.GET)
    public String getIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountService.findAccountByUser(auth.getName());
        Pageable page = new PageRequest(0, 18, Sort.Direction.DESC, "id");
        Page<Profile> pageSet = profileService.getAllProfiles(page);
        List<Profile> profiles = pageSet.getContent();
        //List<Profile> profiles = profileService.findNewestProfiles();
        if (profiles.size() >= 6) {
            model.addAttribute("profiles1", profiles.subList(0, 6));
        }
        if (profiles.size() >= 12) {
            model.addAttribute("profiles2", profiles.subList(6, 12));
        }
        if (profiles.size() >= 18) {
            model.addAttribute("profiles3", profiles.subList(12, 18));
        }
        
        List<Profile> notAnswered = profileService.findAllNotAnsweredProfiles(user);
        if (!notAnswered.isEmpty()) {
            // Get a random number from the list.
            int randomProfile = new Random().nextInt(notAnswered.size());
            // Then get the id of that profile from the not answered list.
            model.addAttribute("random", notAnswered.get(randomProfile).getId());
        }
        
        return "index";
    }

}
