package wepa.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

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
    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Answer> answers;

 
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

    public List<Answer> getAnswers() {
        if (answers == null) {
            answers = new ArrayList<>();
        }
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
