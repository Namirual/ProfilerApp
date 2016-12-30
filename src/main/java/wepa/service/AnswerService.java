package wepa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Account;
import wepa.domain.Answer;
import wepa.domain.AnswerOption;
import wepa.domain.Profile;
import wepa.domain.ProfileQuestion;
import wepa.repository.AnswerRepository;
import wepa.repository.ProfileQuestionRepository;
import wepa.repository.ProfileRepository;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileQuestionRepository profileQuestionRepository;

    public List<Answer> getAnswersForProfile(Long profileId) {
        Profile profile = profileRepository.findOne(profileId);
        List<ProfileQuestion> questions = profile.getProfileQuestions();
        List<Answer> answers = answerRepository.findByProfileQuestionIn(questions);
        return answers;
    }

    public List<Answer> getUserAnswersForProfile(Long profileId, Account account) {
        Profile profile = profileRepository.findOne(profileId);
        List<ProfileQuestion> questions = profile.getProfileQuestions();
        List<Answer> answers = answerRepository.findByProfileQuestionInAndAccount(questions, account);
        return answers;
    }

    public Integer calculateUserProfilerScore(Account account) {
        List<Profile> profiles = account.getAnsweredProfiles();
        // If there are no answers, just return 0.
        if (profiles.isEmpty()) {
            return 0;
        }
        int totalScore = 0;
        // Loop through the answered profiles.
        for (Profile profile : profiles) {
            // Get answers for one profile.
            List<Answer> answers = getUserAnswersForProfile(profile.getId(), account);
            // Get the questions for one profile.
            List<ProfileQuestion> questions = profile.getProfileQuestions();
            // Start keeping score for this profile.
            int profileScore = 0;
            // Loop the questions.
            for (ProfileQuestion question : questions) {
                // This might be unneccasary, because the questions and answers
                // should be in the same order. But just in case, check that
                // we are only checking the answer for right question.
                for (Answer ans : answers) {
                    if (ans.getProfileQuestion() == question) {
                        // If the answer is correct, add to the profileScore.
                        if (question.getCorrectAnswer() == ans.getAnswer()) {
                            profileScore++;
                        }
                    }
                }
            }
            // multiple the profile by 100 to get percentages.
            totalScore += profileScore * 100 / questions.size();
        }
        // finally count the average of all single profile scores.
        int totalAve = totalScore / profiles.size();
        return totalAve;
    }

    @Transactional
    public Map<ProfileQuestion, Map<AnswerOption, Integer>> calculateAnswerRatesForProfileQuestions(Long profileId) {
        // create a map where each question maps to all answeroptions and all answeroptions map to ints
        Map<ProfileQuestion, Map<AnswerOption, Integer>> rateMap = new HashMap<>();
        Profile profile = profileRepository.findOne(profileId);
        List<ProfileQuestion> questions = profile.getProfileQuestions();
        List<Answer> answers = answerRepository.findByProfileQuestionIn(questions);
        // loop the questions
        for (ProfileQuestion question : questions) {
            // create a key for each question
            rateMap.put(question, new HashMap<>());
            // count number of answers for each question
            int ansCount = 0;
            // loop through the answers
            for (Answer answer : answers) {
                // if it is an answer for the current question then
                if (question == answer.getProfileQuestion()) {
                    // add to the answer count
                    ansCount++;
                    // and map the question to an answerOption key, which
                    // maps to the number of answers with this option
                    Map<AnswerOption, Integer> optionMap = rateMap.get(question);
                    AnswerOption ans = answer.getAnswer();
                    if (optionMap.get(ans) == null) {
                        optionMap.put(ans, 1);
                    } else {
                        optionMap.put(ans, optionMap.get(ans) + 1);
                    }
                }
            }
            // loop again trhough the answerOptions for this question
            for (AnswerOption option : rateMap.get(question).keySet()) {
                // now count what is the percentage of this answeOption
                rateMap.get(question).put(option, rateMap.get(question).get(option) * 100 / ansCount);
            }
        }
        return rateMap;
    }

    public boolean hasUserAnswered(List<ProfileQuestion> questions, Account account) {
        //System.out.println(answerRepository.findByProfileQuestionAndAccount(questions.get(0), account));
        return answerRepository.findByProfileQuestionAndAccount(questions.get(0), account) != null;
    }

    @Transactional
    public Answer createAnswer(Account answerer, ProfileQuestion question, AnswerOption answer) {
        // is the question already answered? Should be controlled elsewhere.
        //if (answerRepository.findByProfileQuestionAndAccount(question, answerer) != null) {
        //    return answerRepository.findByProfileQuestionAndAccount(question, answerer);
        //}
        question = profileQuestionRepository.findOne(question.getId());
        Answer ans = new Answer();
        ans = answerRepository.save(ans);
        ans.setAnswerer(answerer);
        ans.setProfileQuestion(question);
        ans.setAnswer(answer);
        ans.setPostTime(new Date());
        ans = answerRepository.save(ans);
        List<Answer> answers = question.getAnswers();
        answers.add(ans);
        question.setAnswers(answers);
        profileQuestionRepository.saveAndFlush(question);
        return ans;
    }

    @Transactional
    public List<Answer> answerAllQuestions(Account answerer, List<ProfileQuestion> questions, List<AnswerOption> answers) {
        List<Answer> ans = new ArrayList<>();
        questions.stream().forEach((question) -> {
            ans.add(createAnswer(answerer, question, answers.get(questions.indexOf(question))));
        });
        return ans;
    }

    public void deleteAnswersForProfile(Profile profile) {
        List<Answer> answers = getAnswersForProfile(profile.getId());
        for (Answer answer:answers) {
            answerRepository.delete(answer);
        }

    }

}
