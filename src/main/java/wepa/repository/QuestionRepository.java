package wepa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{
    
    public Question findByContent(String content);
    
    List<Question> findByIdIn(List<Long> id);
}
