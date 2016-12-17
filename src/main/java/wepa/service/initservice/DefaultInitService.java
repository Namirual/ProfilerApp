package wepa.service.initservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.*;
import wepa.repository.ProfileQuestionRepository;
import wepa.service.ProfileService;
import wepa.service.QuestionService;
import wepa.service.ReviewService;
import wepa.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultInitService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;
    @Autowired
    private ReviewService reviewService;

    @PostConstruct
    public void initDefaultEntitiesForTesting() {
        Map<Integer, String> answeroptionMap = new HashMap<>();
        answeroptionMap.put(1, "Yes");
        answeroptionMap.put(2, "No");
        Question question = questionService.createQuestion("Onko kivaa?", answeroptionMap);
        System.out.println("Question's answeroptions: " + question.getAnswerOptions());
        Account user = userService.createDefaultUserForTesting();
        Account admin = userService.createDefaultAdminForTesting();
        Profile profile = profileService.createProfileAndAssignToUser(user);
        profile = profileService.findOne(profile);
        user = userService.findUser(user);
        System.out.println("Profile for user " + user + ": " + user.getProfile());
        System.out.println("Profile after creation: " + profile);
        profile = profileService.assignQuestionToProfile(profile, question, question.getAnswerOptions().get(0));
        System.out.println("ProfileQuestions for profile: " + profile.getProfileQuestions());
        System.out.println("Profilequestions.get(0)" + profile.getProfileQuestions().get(0));
        System.out.println("First profileQuestion's Question for profile: " +
                profile.getProfileQuestions().get(0).getQuestion());
        System.out.println("First profileQuestion's Question's answeroptions for profile: " +
                profile.getProfileQuestions().get(0).getQuestion().getAnswerOptions());
        user = userService.findUser(user);
        System.out.println("First profileQuestion's Question's answeroptions for profile of user " + user + ": " +
                user.getProfile().getProfileQuestions().get(0).getQuestion().getAnswerOptions());
        for (AnswerOption ao : user.getProfile().getProfileQuestions().get(0).getQuestion().getAnswerOptions()) {
            System.out.println("First profileQuestion's Question's answeroption's answertext for profile of user " + user + ": " +
                    ao.getAnswerText());
        }
        System.out.println("First profileQuestion's answer's answertext for profile of user " + user + ": " +
                user.getProfile().getProfileQuestions().get(0).getAnswer().getAnswerText());

        reviewService.createReview(profile, admin, "Eka arvionti!");
        admin = userService.findUser(admin);
        System.out.println("Reviews by admin: " + admin.getReviews());
        for (Review review : admin.getReviews()) {
            System.out.println("Review content for review " + review.getTestContent());
        }
    }

}
