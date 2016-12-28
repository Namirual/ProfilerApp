package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Profile;
import wepa.domain.ProfileQuestion;
import wepa.repository.AnswerOptionRepository;
import wepa.repository.ProfileQuestionRepository;
import wepa.repository.ProfileRepository;
import wepa.repository.QuestionRepository;

// This class manages primarily the creation of ProfileQuestions, which are
// added to profiles.

@Service
public class ProfileQuestionService {
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private AnswerOptionRepository answerOptionRepository;
    
    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;
    
    
    
    @Transactional
    public ProfileQuestion assignQuestionToProfile(Long profileId, Long questionId, Long answerId) {
        // Check that parameters are valid.
        if (!profileRepository.exists(profileId) || !questionRepository.exists(questionId) 
                || !answerOptionRepository.exists(answerId)) {
            return null;
        }
        
        ProfileQuestion profileQuestion = new ProfileQuestion();
        Profile profile = profileRepository.findOne(profileId);
        profileQuestion.setProfile(profile);
        profileQuestion.setQuestion(questionRepository.findOne(questionId));
        profileQuestion.setCorrectAnswer(answerOptionRepository.findOne(answerId));
        // Check if the current question is already in the profile.
        if (profile.getProfileQuestions().contains(profileQuestion)) {
            // If so, return the existing question.
            return profile.getProfileQuestions().get(profile.getProfileQuestions().indexOf(profileQuestion));
        }
        
        profileQuestion = profileQuestionRepository.save(profileQuestion);
        profile.addProfileQuestion(profileQuestion);
        profileRepository.save(profile);
        return profileQuestion;
    }
    
    /*@Transactional
    public boolean assignListOfProfileQuestions(String profileId, List<ProfileQuestion> questions) {
        Profile profile = profileRepository.findOne(profileId);
        if (profile == null) {
            return false;
        }
        List<ProfileQuestion> profileQuestions = profile.getProfileQuestions();
        for (ProfileQuestion question : profileQuestions) {
            question = profileQuestionRepository.findOne(question.getId());
            ProfileQuestion profileQuestion = new ProfileQuestion();
            profileQuestion.setProfile(profile);
            profileQuestion.setDbQuestion(DBQuestion);
            profileQuestion = profileQuestionRepository.save(profileQuestion);
            profileQuestions.add(profileQuestion);
        }
        profile.setProfileQuestions(profileQuestions);
        return profileRepository.save(profile);
    }*/
}
