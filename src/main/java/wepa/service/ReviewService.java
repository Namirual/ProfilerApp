package wepa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wepa.domain.Account;
import wepa.domain.Profile;
import wepa.domain.Review;
import wepa.repository.AccountRepository;
import wepa.repository.ProfileRepository;
import wepa.repository.ReviewRepository;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProfileRepository profileRepository;
//    @Autowired
//    private
    @Transactional
    public Review createReview (Profile profile, Account reviewer, String reviewContent) {
        profile = profileRepository.findOne(profile.getId());
        reviewer = accountRepository.findOne(reviewer.getId());
        Review review = new Review();
        review = reviewRepository.save(review);
        review.setReviewedProfile(profile);
        review.setTestContent(reviewContent);
        review.setReviewerAccount(reviewer);
        return review;
    }
}
