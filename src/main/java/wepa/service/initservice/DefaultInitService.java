package wepa.service.initservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.*;
import wepa.repository.ProfileQuestionRepository;
import wepa.repository.QuestionRepository;
import wepa.service.*;

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
    @Autowired
    private QuestionRepository dbQuestionRepository;
    @Autowired
    private ViewService viewService;

    @PostConstruct
    public void initDefaultEntitiesForTesting() {
        if(dbQuestionRepository.findByContent("Alkuperä?") !=null) {
            return;
        }
        Map<Integer, String> answeroptionMap = new HashMap<>();
        answeroptionMap.put(1, "Toki!");
        answeroptionMap.put(2, "Ei!");
        answeroptionMap.put(3, "Ei koskaan!");
        DBQuestion dbQuestion = questionService.createQuestion("Onko kivaa?", answeroptionMap);

        answeroptionMap.clear();
        answeroptionMap.put(1, "10–15");
        answeroptionMap.put(2, "16–20");
        answeroptionMap.put(3, "21–25");
        DBQuestion dbQuestion2 = questionService.createQuestion("Ikä?", answeroptionMap);

        answeroptionMap.clear();
        answeroptionMap.put(1, "10–20");
        answeroptionMap.put(2, "20–30");
        answeroptionMap.put(3, "30–40");
        DBQuestion dbQuestion3 = questionService.createQuestion("Henkinen ikä?", answeroptionMap);

        answeroptionMap.clear();
        answeroptionMap.put(1, "Murrica");
        answeroptionMap.put(2, "Yurop");
        answeroptionMap.put(3, "Azn");
        DBQuestion dbQuestion4 = questionService.createQuestion("Alkuperä?", answeroptionMap);

        System.out.println("dbQuestion's answeroptions: " + dbQuestion.getAnswerOptions());
        System.out.println("dbQuestion2's answeroptions: " + dbQuestion2.getAnswerOptions());
        System.out.println("dbQuestion3's answeroptions: " + dbQuestion3.getAnswerOptions());
        System.out.println("dbQuestion4's answeroptions: " + dbQuestion4.getAnswerOptions());

        Account user = userService.createDefaultUserForTesting();
        Account admin = userService.createDefaultAdminForTesting();

        Profile profile = profileService.createProfileAndAssignToUser(user);
        Profile profile2 = profileService.createProfileAndAssignToUser(admin);
        profile = profileService.findOne(profile);
        profile2 = profileService.findOne(profile2);
        user = userService.findUser(user);
        admin = userService.findUser(admin);
        System.out.println("User's profile's timeStamp: " + user.getProfile().getCreationTimeInMillis());
        System.out.println("Admin's profile's timeStamp: " + admin.getProfile().getCreationTimeInMillis());
        System.out.println("Newest profile's user: " + viewService.findNewestProfile().getOwnerAccount().getUsername());

        profile = profileService.assignQuestionToProfile(profile, dbQuestion, dbQuestion.getAnswerOptions().get(0));
        profile = profileService.assignQuestionToProfile(profile, dbQuestion2, dbQuestion2.getAnswerOptions().get(0));
        profile = profileService.assignQuestionToProfile(profile, dbQuestion3, dbQuestion3.getAnswerOptions().get(0));
        profile = profileService.assignQuestionToProfile(profile, dbQuestion4, dbQuestion4.getAnswerOptions().get(0));

        profile2 = profileService.assignQuestionToProfile(profile2, dbQuestion, dbQuestion.getAnswerOptions().get(1));
        profile2 = profileService.assignQuestionToProfile(profile2, dbQuestion2, dbQuestion2.getAnswerOptions().get(1));
        profile2 = profileService.assignQuestionToProfile(profile2, dbQuestion3, dbQuestion3.getAnswerOptions().get(1));
        profile2 = profileService.assignQuestionToProfile(profile2, dbQuestion4, dbQuestion4.getAnswerOptions().get(1));
        System.out.println("ProfileQuestions for profile: " + profile.getProfileQuestions());
        System.out.println("ProfileQuestions for profile2: " + profile2.getProfileQuestions());
        System.out.println("profile.getProfilequestions.get(0)" + profile.getProfileQuestions().get(0));
        System.out.println("profile2.getProfilequestions.get(0)" + profile2.getProfileQuestions().get(0));

        System.out.println("First profileQuestion's dbQuestion for profile: " +
                profile.getProfileQuestions().get(0).getDbQuestion());
        System.out.println("First profileQuestion's dbQuestion for profile2: " +
                profile2.getProfileQuestions().get(0).getDbQuestion());

        System.out.println("First profileQuestion's dbQuestion's answeroptions for profile: " +
                profile.getProfileQuestions().get(0).getDbQuestion().getAnswerOptions());
        System.out.println("First profileQuestion's dbQuestion's answeroptions for profile2: " +
                profile2.getProfileQuestions().get(0).getDbQuestion().getAnswerOptions());

        user = userService.findUser(user);
        admin = userService.findUser(admin);

        System.out.println("First profileQuestion's dbQuestion's answeroptions for profile of user " + user + ": " +
                user.getProfile().getProfileQuestions().get(0).getDbQuestion().getAnswerOptions());
        System.out.println("First profileQuestion's dbQuestion's answeroptions for profile of admin " + admin + ": " +
                user.getProfile().getProfileQuestions().get(0).getDbQuestion().getAnswerOptions());

        for (AnswerOption ao : user.getProfile().getProfileQuestions().get(0).getDbQuestion().getAnswerOptions()) {
            System.out.println("First profileQuestion's dbQuestion's answeroption's answertext for profile of user " + user + ": " +
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
