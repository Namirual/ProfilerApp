package wepa.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import wepa.repository.UUIDPersistable;
import javax.persistence.*;


@Entity
public class ProfileQuestion extends UUIDPersistable {

    @ManyToOne(cascade = javax.persistence.CascadeType.ALL)
    private Profile profile;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = javax.persistence.CascadeType.ALL)
    private Question question;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne
    private  AnswerOption answer;

    public AnswerOption getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerOption answer) {
        this.answer = answer;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
