package wepa.domain;


import wepa.repository.UUIDPersistable;
import javax.persistence.*;

@Entity
public class AnswerOption extends UUIDPersistable {
    @ManyToOne
    private DBQuestion DBQuestion;
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

    public DBQuestion getDBQuestion() {
        return DBQuestion;
    }

    public void setDBQuestion(DBQuestion DBQuestion) {
        this.DBQuestion = DBQuestion;
    }

}
