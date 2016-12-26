package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{
    public Question findByContent(String content);
}
