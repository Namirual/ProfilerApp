package wepa.domain;


import wepa.repository.UUIDPersistable;
import javax.persistence.*;

@Entity
public class AnswerOption extends UUIDPersistable {
    @ManyToOne
    private DBQuestion dbQuestion;
    @OneToOne(mappedBy = "answer", fetch = FetchType.EAGER)
    private ProfileQuestion profileQuestion;
    private String answerText;
    private Integer orderNumber;

    public ProfileQuestion getProfileQuestion() {
        return profileQuestion;
    }

    public void setProfileQuestion(ProfileQuestion profileQuestion) {
        this.profileQuestion = profileQuestion;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public DBQuestion getDbQuestion() {
        return dbQuestion;
    }

    public void setDbQuestion(DBQuestion dbQuestion) {
        this.dbQuestion = dbQuestion;
    }

}
