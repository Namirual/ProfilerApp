package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.AnswerOption;


public interface AnswerOptionRepository extends JpaRepository<AnswerOption, String> {
}
