package wepa.domain;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import wepa.repository.UUIDPersistable;
import javax.persistence.*;

@Entity
public class ReviewQuestion extends UUIDPersistable {
    @ManyToOne(fetch = FetchType.EAGER)
    private Review review;
    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToOne(cascade = javax.persistence.CascadeType.ALL)
    private DBQuestion DBQuestion;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne
    private  AnswerOption reviewAnswer;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToOne
    private  AnswerOption rightAnswer;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public DBQuestion getDBQuestion() {
        return DBQuestion;
    }

    public void setDBQuestion(DBQuestion DBQuestion) {
        this.DBQuestion = DBQuestion;
    }

    public AnswerOption getReviewAnswer() {
        return reviewAnswer;
    }

    public void setReviewAnswer(AnswerOption reviewAnswer) {
        this.reviewAnswer = reviewAnswer;
    }

    public AnswerOption getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(AnswerOption rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
