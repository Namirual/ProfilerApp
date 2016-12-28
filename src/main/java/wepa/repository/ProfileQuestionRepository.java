package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.ProfileQuestion;
import wepa.domain.Question;

public interface ProfileQuestionRepository  extends JpaRepository<ProfileQuestion, Long>{
    
    ProfileQuestion findByQuestion(Question question);
    
}
