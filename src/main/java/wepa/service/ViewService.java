package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wepa.domain.Profile;
import wepa.domain.ProfileQuestion;
import wepa.repository.AccountRepository;
import wepa.repository.ProfileRepository;

import java.util.List;

//@Service
public class ViewService {
    /*@Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public Profile findProfileById(String id) {
        return profileRepository.findOne(id);
    }

    public Profile findNewestProfile() {
        return profileRepository.findFirstByOrderByCreationTimeInMillisDesc();
    }

    public Profile findNextProfile(Profile current) {
//        Finds OLDER profile.
        return profileRepository.findFirstByCreationTimeInMillisLessThan(current.getCreationTimeInMillis());
    }
    public Profile findPreviousProfile(Profile current) {
//        Finds NEWER profile.
        return profileRepository.findFirstByCreationTimeInMillisLessThan(current.getCreationTimeInMillis());
    }

    public List<ProfileQuestion> getProfileQuestionsByProfile(Profile profile) {
        return profile.getProfileQuestions();
    }

*/
}
