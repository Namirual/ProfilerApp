package wepa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
            // here go through the questions
            for (ProfileQuestion question : profileQuestions) {
                AnswerOption mostAnswered = null;
                int highestRate = 0;
                Map<AnswerOption, Integer> questionInfo = ansRate.get(question);
                // and here go through all the answers to the questions
                for (AnswerOption option : questionInfo.keySet()) {
                    // if a given answer has more votes than the previous ones
                    // then set it as the mostAnswered one
                    if (questionInfo.get(option) > highestRate) {
                        mostAnswered = option;
                        highestRate = questionInfo.get(option);
                    }
                }
                options.add(mostAnswered);
                rates.add(highestRate);
            }
            // only add the most popular answers if there are any
            if (!options.contains(null)) {
                model.addAttribute("options", options);
                model.addAttribute("rates", rates);
            }
        }
        // If the user has not reviewed the profile, show only the questions
        // with the answer options.
        model.addAttribute("questions", questions);
        if (!ownAns.isEmpty()) {
            model.addAttribute("ownans", ownAns);
        }

        // Add also the profile's id number.
        model.addAttribute("id", id);

        // The id of the profile picture is added.
        model.addAttribute("profilePic", profile.getProfilePicId());

        // We also need links to next and previous profiles..
        Profile previous = profileService.findPreviousProfile(id);
        Profile next = profileService.findNextProfile(id);
        if (previous != null) {
            model.addAttribute("previous", previous.getId());
        }
        if (next != null) {
            model.addAttribute("next", next.getId());
        }
        // ..and to some random profile that has not been answered yet.
        List<Profile> notAnswered = profileService.findAllNotAnsweredProfiles(user);
        if (!notAnswered.isEmpty()) {
            // Get a random number from the list.
            int randomProfile = new Random().nextInt(notAnswered.size());
            // Then get the id of that profile from the not answered list.
            model.addAttribute("random", notAnswered.get(randomProfile).getId());
        }

        // We have three different pages to simplify the Thymeleaf needed.
        if (user == profile.getOwnerAccount()) {
            return "ownprofile";
        } else if (ownAns.isEmpty()) {
            return "answerprofile";
        } else {
            return "ratedprofile";
        }
    }

    @RequestMapping(value = "/{id}/answer", method = RequestMethod.POST)
    public String postAnswers(@PathVariable Long id, @RequestParam List<Integer> answerId) {
        // Get user authentication.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Get the account
        Account answerer = accountService.findAccountByUser(auth.getName());
        // Get the profile
        Profile profile = profileService.findProfileById(id);
        // If there is no such profile, give some other page.
        if (profile == null) {
            return "redirect:/profiles/" + id;
        }
        // Get the ProfileQuestions
        List<ProfileQuestion> profileQuestions = profile.getProfileQuestions();
        // Get the AnswerOptions selected for each question
        List<AnswerOption> answers = new ArrayList<>();
        for (ProfileQuestion question : profileQuestions) {
            List<AnswerOption> options = question.getQuestion().getAnswerOptions();
            int questionInd = profileQuestions.indexOf(question);
            int ansInd = answerId.get(questionInd);
            AnswerOption ans = options.get(ansInd);
            answers.add(ans);
        }
        // Save the actual answers to database
        answerService.answerAllQuestions(answerer, profileQuestions, answers);
        accountService.addAnswerToAccount(answerer, profile);
        return "redirect:/profiles/" + id;
    }
}
