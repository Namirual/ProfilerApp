package wepa.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wepa.domain.Account;
import wepa.domain.Answer;
import wepa.domain.AnswerOption;
import wepa.domain.Profile;
import wepa.domain.ProfileQuestion;
import wepa.domain.Question;
import wepa.service.AccountService;
import wepa.service.AnswerService;
import wepa.service.ProfileQuestionService;
import wepa.service.ProfileService;

@Controller
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileQuestionService profileQuestionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AnswerService answerService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String viewProfile(Model model, @PathVariable Long id) {
        // Get user authentication.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountService.findAccountByUser(auth.getName());
        // Get the profile
        Profile profile = profileService.findProfileById(id);
        // If there is no such profile, give some other page.
        if (profile == null) {
            return "index";
        }

        List<Question> questions = new ArrayList<>();
        List<ProfileQuestion> profileQuestions = profile.getProfileQuestions();
        List<AnswerOption> ownAns = new ArrayList<>();
        boolean userAnswered = answerService.hasUserAnswered(profileQuestions, user);

        for (ProfileQuestion question : profileQuestions) {
            // Get the questions for the profile
            questions.add(question.getQuestion());
            // if user is the profile's creator, show the correct responses.
            if (user == profile.getOwnerAccount()) {
                ownAns.add(question.getCorrectAnswer());
            }
        }
         // If it is another user, but who has reviewed the profile, show
        // his own responses.
        if (userAnswered && user != profile.getOwnerAccount()) {
            List<Answer> userAns = answerService.getUserAnswersForProfile(id, user);
            for (Answer a : userAns) {
                ownAns.add(a.getAnswer());
            }
        }
        // In both cases show also the community results.
        if (user == profile.getOwnerAccount() || userAnswered) {
            Map<ProfileQuestion, Map<AnswerOption, Integer>> ansRate = answerService.calculateAnswerRatesForProfileQuestions(id);
            List<AnswerOption> options = new ArrayList<>();
            List<Integer> rates = new ArrayList<>();
            for(ProfileQuestion question : profileQuestions) {
                AnswerOption mostAnswered = null;
                int highestRate = 0;
                Map<AnswerOption,Integer> questionInfo = ansRate.get(question);
                for(AnswerOption option : questionInfo.keySet()) {
                    if(questionInfo.get(option) > highestRate) {
                        mostAnswered = option;
                        highestRate = questionInfo.get(option);
                    }
                }
                options.add(mostAnswered);
                rates.add(highestRate);
            }
            model.addAttribute("options", options);
            model.addAttribute("rates", rates);
        }
        // If the user has not reviewed the profile, show only the questions
        model.addAttribute("questions", questions);
        if(!ownAns.isEmpty()) {
            model.addAttribute("ownans", ownAns);
        }
        // with the answer options.
        model.addAttribute("profile", profile);
        return "profile";
    }
}
