package wepa.domain;

import java.util.Objects;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

// This class holds all the representations of the user-selected questions,
// and also the correct answer option for this particular question. It knows
// also with which profile it is associated with. Note then that the question
// is not associated with any user, but a profile created by some user.

@Entity
public class ProfileQuestion extends AbstractPersistable<Long> {

    @ManyToOne
    private Profile profile;
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne
    private Question question;
    
    @ManyToOne
    private AnswerOption correctAnswer;

 
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

    public AnswerOption getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(AnswerOption correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProfileQuestion other = (ProfileQuestion) obj;
        if (!Objects.equals(this.question, other.question)) {
            return false;
        }
        return true;
    }
    
}
