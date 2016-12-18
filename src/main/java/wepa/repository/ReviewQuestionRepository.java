package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.ReviewQuestion;

public interface ReviewQuestionRepository extends JpaRepository<ReviewQuestion, String>{
}
