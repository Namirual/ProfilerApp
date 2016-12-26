package wepa.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

// Each instance of this class holds one response option for a particular
// question. It is used both as the type for the correct answer and as the
// type of the answer that each user gives.

@Entity
public class AnswerOption extends AbstractPersistable<Long> {
    
    @ManyToOne //needs mappedby and join-column notations
    private Question question;
//    @OneToOne(mappedBy = "answer", fetch = FetchType.EAGER)
//    private ProfileQuestion profileQuestion;
    private String answerText;
    private Integer orderNumber;

    
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
