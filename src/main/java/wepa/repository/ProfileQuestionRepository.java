package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.ProfileQuestion;


public interface ProfileQuestionRepository  extends JpaRepository<ProfileQuestion, String>{
}
