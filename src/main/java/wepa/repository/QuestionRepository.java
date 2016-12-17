package wepa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.DBQuestion;

public interface QuestionRepository extends JpaRepository<DBQuestion, String>{
}
