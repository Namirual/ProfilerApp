/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wepa.service.initservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import wepa.domain.Account;
import wepa.domain.AnswerOption;
import wepa.domain.Profile;
import wepa.domain.Question;
import wepa.domain.ProfileQuestion;

import wepa.repository.AccountRepository;
import wepa.repository.ProfileRepository;
import wepa.service.AccountService;
import wepa.service.AnswerService;
import wepa.service.ImageObjectService;
import wepa.service.ProfileQuestionService;
import wepa.service.ProfileService;
import wepa.service.QuestionService;

/**
 *
 * @author lmantyla
 */
@Service
public class InitService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute
    private Account getAccount() {
        return new Account();
    }

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileQuestionService profileQuestionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ImageObjectService imageService;

    @Autowired
    private AnswerService answerService;

    // Testausta varten, otetaan pois ennen kuin tämä valmistuu
    @PostConstruct
    private void init() {

        Question question1 = questionService.createQuestion("How old is this person?", Arrays.asList("Still a child", "20-25", "25-30", "30-35", "35-40", "old as fuck"));
        Question question2 = questionService.createQuestion("How tall is this person?", Arrays.asList("Midget", "140-150", "150-160", "160-170", "170-180", "180-190", "190-200", "HUGE"));
        Question question3 = questionService.createQuestion("Does this person look like a dog or a cat person?", Arrays.asList("Neither", "Dog", "Cat", "Both"));
        Question question4 = questionService.createQuestion("Does this person look like Bitch?", Arrays.asList("No", "Yes", "What?"));

        Account account1 = createAccount("Gilgamesh", "user", "user", "gilgamesh@sumer.com", false);
        Account account2 = createAccount("Assurnasirpal", "user2", "user2", "assurnaspl@ashur.com", false);
        Account account3 = createAccount("Minos", "user3", "user3", "minos@crete.com", false);
        Account account4 = createAccount("Hippo", "user4", "user4", "hippo@nile.com", false);
        Account account5 = createAccount("Inanna", "admin", "admin", "ishtar@arbela.com", true);

        Profile profile1 = createTestProfile(account1, Arrays.asList(question1, question2), "public/img/gilga.jpg");
        Profile profile2 = createTestProfile(account2, Arrays.asList(question1, question4), "public/img/assur.jpg");
        Profile profile3 = createTestProfile(account2, Arrays.asList(question1, question4), "public/img/eunuch.jpg");
        Profile profile4 = createTestProfile(account3, Arrays.asList(question1, question2, question3), "public/img/minoan.jpg");
        Profile profile5 = createTestProfile(account4, Arrays.asList(question1, question2, question3, question4), "public/img/hippo.jpg");
        Profile profile6 = createTestProfile(account5, Arrays.asList(question1, question2), "public/img/ishtar.jpg");
        Profile profile7 = createTestProfile(account5, Arrays.asList(question1, question2), "public/img/alabaster.jpg");

        createTestAnswers(account1, profile2, Arrays.asList(5,2));
        createTestAnswers(account2, profile3, Arrays.asList(2,1));
        createTestAnswers(account3, profile2, Arrays.asList(5,1));

        createTestAnswers(account4, profile1, Arrays.asList(5,7));
        createTestAnswers(account4, profile2, Arrays.asList(5,2));
        createTestAnswers(account4, profile3, Arrays.asList(5,2));

    }

    private Account createAccount(String name, String username, String password, String email, boolean admin) {
        Account account = new Account();
        account.setName(name);
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setEmail(email);
        if (admin) {
            account.setAuthorities(Arrays.asList("ADMIN"));
        } else {
            account.setAuthorities(Arrays.asList("USER"));
        }
        account = accountRepository.save(account);
        account.setAnsweredProfiles(new ArrayList<Profile>());
        return account;
    }

    private Profile createTestProfile(Account account, List<Question> questions, String imagePath) {
        Profile profile = profileService.createProfileAndAssignToUser(account);
        profile = profileRepository.save(profile);

        profile.setProfileQuestions(new ArrayList<>());

        for (Question question : questions) {
            Long answerId = question.getAnswerOptions().get(question.getAnswerOptions().size() - 1).getId();
            profileQuestionService.assignQuestionToProfile(profile.getId(), question.getId(), answerId);
        }
        profile = profileService.findOne(profile);
        List<String> images = imageService.createImageObject(getImage(imagePath));
        profile.setProfilePicId(images.get(0));
        profile.setThumbnailId(images.get(1));

        return profileRepository.save(profile);
    }

    private MultipartFile getImage(String imagePath) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(imagePath);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            IOUtils.copy(is, outputStream);
        } catch (IOException ioe) {
            System.out.println("Problem while reading from the file: " + ioe.getMessage());
            return null;
        }

        byte[] bytes = outputStream.toByteArray();
        MultipartFile multipartFile = new MockMultipartFile("file", "hippo.jpg", "image/jpeg", bytes);
        return multipartFile;
    }

    private void createTestAnswers(Account account, Profile profile, List<Integer> answerNum) {
        List<AnswerOption> answers = new ArrayList<>();
        int num = 0;
        
        for (ProfileQuestion profQuestion : profile.getProfileQuestions()) {
            answers.add(profQuestion.getQuestion().getAnswerOptions().get(answerNum.get(num)));
            num++;
        }

        answerService.answerAllQuestions(account, profile.getProfileQuestions(), answers);
        accountService.addAnswerToAccount(account, profile);
    }
}
