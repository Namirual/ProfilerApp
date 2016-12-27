package wepa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wepa.domain.Profile;
import wepa.service.ProfileService;

@Controller
@RequestMapping("/profiles")
public class ProfileController {
    
    @Autowired
    private ProfileService profileService;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String viewProfile(Model model, @PathVariable String id) {
        Profile profile = profileService.findProfileById(id);
        //get user authentication.
        // if user is the profile's creator, show the correct responses.
        // If it is another user, but who has reviewed the profile, show
        // his own responses.
        // In both cases show also the community results.
        // If the user has not reviewed the profile, show only the questions
        // with the answer options.
        model.addAttribute("profile", profile);
        return "profile";
    }
}
