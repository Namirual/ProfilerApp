package wepa.controller;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wepa.domain.Account;
import wepa.domain.Profile;
import wepa.domain.Question;
import wepa.service.AccountService;
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
    
    @PostConstruct
    private void initQuestions() {
        questionService.createQuestion("How old is this person?", Arrays.asList("Still a child","20-25","25-30","30-35","35-40","old as fuck"));
        questionService.createQuestion("How tall is this person?", Arrays.asList("Midget","140-150","150-160","160-170","170-180","180-190","190-200","HUGE"));
        questionService.createQuestion("Does this person look like a dog or a cat person?", Arrays.asList("Neither","Dog","Cat","Both"));
        questionService.createQuestion("Does this person look like Bitch?", Arrays.asList("No","Yes","What?"));
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String viewCreateProfile(Model model) {
        // Get the user authentication
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        model.addAttribute("questions", questionService.findAllQuestions());
        
        return "create_profile";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String postNewProfile(@RequestParam List<Long> questions, @RequestParam List<Integer> answerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountService.findAccountByUser(auth.getName());
        Profile profile = profileService.createProfileAndAssignToUser(user);
        List<Question> list = questionService.findManyQuestions(questions);
        for (Question question : list) {
            // get index of the question
            Long questionIndex = question.getId();
            // get index of the answer
            Long answer = question.getAnswerOptions().get(answerId.get(questionIndex.intValue()-1)).getId();
            profileQuestionService.assignQuestionToProfile(profile.getId(), questionIndex, answer);
            //System.out.println(question.getContent() + ", index: " + questionIndex);
            //System.out.println(question.getAnswerOptions().get(answerId.get(questionIndex-1)).getAnswerText());
        }
        return"redirect:/userpage";
    }
    
}
