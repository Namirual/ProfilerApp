package wepa.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.format.annotation.DateTimeFormat;

// This class holds the actual answers for each profile question. It tells to 
// what question it answering and the person who posted this answer.

@Entity
public class Answer extends AbstractPersistable<Long> {
    
    @ManyToOne
    private ProfileQuestion profileQuestion;
    
    @ManyToOne
    private AnswerOption answer;
    
    @ManyToOne
    private Account account;
    
    @DateTimeFormat
    private Date postTime;

    
    public ProfileQuestion getProfileQuestion() {
        return profileQuestion;
    }

    public void setProfileQuestion(ProfileQuestion profileQuestion) {
        this.profileQuestion = profileQuestion;
    }

    public AnswerOption getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerOption answer) {
        this.answer = answer;
    }

    public Account getAnswerer() {
        return account;
    }

    public void setAnswerer(Account answerer) {
        this.account = answerer;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }
    
}
 
