package wepa.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import wepa.repository.UUIDPersistable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class DBQuestion extends UUIDPersistable {
    private String content;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "DBQuestion")
    private List<AnswerOption> answerOptions;
    @OneToMany(mappedBy = "DBQuestion")
    private List<ProfileQuestion> profileQuestions;
    @OneToMany(mappedBy = "DBQuestion")
    private List<ReviewQuestion> reviewQuestions;

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public String getAnswerOptionsAsString() {
        String aoString = "[";
        for (AnswerOption ao : answerOptions) {
            aoString += ao.getAnswerText() + ", ";
        }
        aoString = aoString.substring(0, aoString.length() - 2);
        aoString += "]";
        return aoString;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ProfileQuestion> getProfileQuestions() {
        return profileQuestions;
    }

    public void setProfileQuestions(List<ProfileQuestion> profileQuestions) {
        this.profileQuestions = profileQuestions;
    }

    public List<ReviewQuestion> getReviewQuestions() {
        return reviewQuestions;
    }

    public void setReviewQuestions(List<ReviewQuestion> reviewQuestions) {
        this.reviewQuestions = reviewQuestions;
    }
}
