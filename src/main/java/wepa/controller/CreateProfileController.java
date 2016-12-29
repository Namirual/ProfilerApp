package wepa.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wepa.domain.Account;
import wepa.domain.AnswerOption;
import wepa.domain.Profile;
import wepa.domain.Question;
import wepa.service.AccountService;
import wepa.service.ImageObjectService;
import wepa.service.ProfileQuestionService;
import wepa.service.ProfileService;
import wepa.service.QuestionService;

@Controller
@RequestMapping("/createprofile")
public class CreateProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ProfileQuestionService profileQuestionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ImageObjectService imageService;

    @PostConstruct
    private void initQuestions() {
        questionService.createQuestion("How old is this person?", Arrays.asList("Still a child", "20-25", "25-30", "30-35", "35-40", "old as fuck"));
        questionService.createQuestion("How tall is this person?", Arrays.asList("Midget", "140-150", "150-160", "160-170", "170-180", "180-190", "190-200", "HUGE"));
        questionService.createQuestion("Does this person look like a dog or a cat person?", Arrays.asList("Neither", "Dog", "Cat", "Both"));
        questionService.createQuestion("Does this person look like Bitch?", Arrays.asList("No", "Yes", "What?"));
    }

    
    @RequestMapping(method = RequestMethod.GET)
    public String viewCreateProfile(Model model) {
        // Get the user authentication
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("questions", questionService.findAllQuestions());

        return "create_profile";
    }

    
    @RequestMapping(method = RequestMethod.POST)
    public String postNewProfile(@RequestParam List<String> answerId,
            @RequestParam("file") MultipartFile file) {

        List<String> images = imageService.createImageObject(file);

        //If the imageService rejects the file, profile creation is interrupted.
        if (images == null) {
            return "redirect:/userpage";
        }

        String imageId = images.get(0);
        String thumbnailId = images.get(1);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountService.findAccountByUser(auth.getName());
        Profile profile = profileService.createProfileAndAssignToUser(user);

        profile.setProfilePicId(imageId);
        profile.setThumbnailId(thumbnailId);
        // Since the answers are send in a string list of question-answer pairs,
        // it has to be parsed first into a Long-Long map.

        Map<Long, Long> questionsAndAnswers = parseStringListIntoMap(answerId);
        for (Long key :questionsAndAnswers.keySet()) {
            if(questionsAndAnswers.get(key) != 0) {
                profileQuestionService.assignQuestionToProfile(profile.getId(), key, questionsAndAnswers.get(key));
            }
        }

        return "redirect:/userpage";
    }


    private Map<Long, Long> parseStringListIntoMap(List<String> list) {
        Map<Long, Long> map = new HashMap<>();
        for (String str : list) {
            String[] splitStr = str.split(",");
            Long key = Long.parseLong(splitStr[0]);
            Long value = Long.parseLong(splitStr[1]);
            map.put(key, value);
        }
        return map;
    }
    
}
