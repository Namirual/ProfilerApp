package wepa.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import wepa.repository.UUIDPersistable;
import javax.persistence.*;
import java.util.List;


@Entity
public class Review extends UUIDPersistable {
    @ManyToOne(fetch = FetchType.EAGER)
    private Account reviewerAccount;
    @ManyToOne(fetch = FetchType.EAGER)
    private Profile reviewedProfile;
    private String testContent;
    @LazyCollection(LazyCollectionOption.FALSE)
    @PrimaryKeyJoinColumn
    @OneToMany(mappedBy = "review")
    private List<ReviewQuestion> reviewedQuestions;

    public Account getReviewerAccount() {
        return reviewerAccount;
    }

    public void setReviewerAccount(Account account) {
        this.reviewerAccount = account;
    }

    public Profile getReviewedProfile() {
        return reviewedProfile;
    }

    public void setReviewedProfile(Profile reviewedProfile) {
        this.reviewedProfile = reviewedProfile;
    }

    public String getTestContent() {
        return testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    public List<ReviewQuestion> getReviewedQuestions() {
        return reviewedQuestions;
    }

    public void setReviewedQuestions(List<ReviewQuestion> reviewedQuestions) {
        this.reviewedQuestions = reviewedQuestions;
    }
}
