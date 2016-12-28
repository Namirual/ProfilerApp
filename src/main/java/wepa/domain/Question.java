package wepa.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.springframework.data.jpa.domain.AbstractPersistable;

// This class holds all the questions that can be added to any profile.
// It also contains all the possible answer options to the question.

@Entity
public class Question extends AbstractPersistable<Long> {
    
    @OneToMany(mappedBy = "question")
    private List<AnswerOption> answerOptions;
    
    private String content;

    // Argumentless constructor is mandatory. 
    // TODO: Should answerOption be initilized here as well?
    public Question() {   
    }
    
    public Question(String question) {
        this.content = question;
        this.answerOptions = new ArrayList<>();
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
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
    
}
