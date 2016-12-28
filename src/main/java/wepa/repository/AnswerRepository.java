package wepa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.Account;
import wepa.domain.Answer;
import wepa.domain.ProfileQuestion;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    
    List<Answer> findByProfileQuestion(List<ProfileQuestion> profileQuestion);
    
    List<Answer> findByProfileQuestionAndAccount(List<ProfileQuestion> profileQuestion, Account account);
    
    List<Answer> findByProfileQuestion(ProfileQuestion profileQuestion);
    
    Answer findByProfileQuestionAndAccount(ProfileQuestion profileQuestion, Account account);
    
}
