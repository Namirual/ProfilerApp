package wepa.service;

import java.util.ArrayList;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.*;
import wepa.repository.*;

import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AnswerOptionRepository answerOptionRepository;

    
    @Transactional
    public Profile createProfileAndAssignToUser(Account account) {
        account = accountRepository.findOne(account.getId());
        if (account == null) {
            return null;
        }
        Profile profile = new Profile();
        profile = profileRepository.save(profile);
        account.addProfile(profile);
        account = accountRepository.save(account);
        profile.setOwnerAccount(account);
        profile.setCreationTime(new Date());
        profile = profileRepository.save(profile);
        account = accountRepository.save(account);
        return profile;
    }

    @Transactional
    public boolean setProfileActive(Long profileId) {
        Profile profile = profileRepository.findOne(profileId);
        if (profile == null) {
            return false;
        }
        // Since only one profile can be active at a time, all other profiles
        // are set inactive.
        List<Profile> profiles = profileRepository.findByOwnerAccount(profile.getOwnerAccount());
        profiles.stream().forEach((p) -> {
            if (p.getId().equals(profileId)) {
                p.setActive(true);
            } else {
                p.setActive(false);
            }
        });
        return true;
    }
    
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile findOne(Profile profile) {
        return profileRepository.findOne(profile.getId());
    }

    public List<Profile> findProfilesByAccount(Account account) {
        return profileRepository.findByOwnerAccount(account);
    }

    public Profile findProfileById(Long id) {
        return profileRepository.findOne(id);
    }

    public Profile findNextProfile(Long id) {
        return profileRepository.findFirstByIdGreaterThan(id);
    }

    public Profile findPreviousProfile(Long id) {
        return profileRepository.findFirstByIdLessThanOrderByIdDesc(id);
    }
    
    @Transactional
    public List<Profile> findAllNotAnsweredProfiles(Account account) {
        // first get all answered profiles
        List<Profile> profiles = account.getAnsweredProfiles();
        // if there are no answers, just return all profiles.
        if (profiles.isEmpty()) {
            List<Profile> notAnswered = profileRepository.findAll();
            notAnswered.removeAll(account.getProfiles());
            return notAnswered;
        }
        // else get ids for all the profiles
        List<Long> profileIds = new ArrayList<>();
        for (Profile profile : profiles) {
            profileIds.add(profile.getId());
        }
        // find all profiles, except those that are answered
        List<Profile> notAnswered = profileRepository.findByIdNotIn(profileIds);
        // also need to remove all the user's own profiles from the list
        notAnswered.removeAll(account.getProfiles());
        return notAnswered;
    }

    public List<Profile> findNewestProfiles() {
        return profileRepository.findFirst10ByOrderByIdDesc();
    }

}
