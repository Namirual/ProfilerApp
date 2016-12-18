package wepa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wepa.domain.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    Account findByUsername(String username);
    Account findFirstByOrderByCreationTimeInMillisDesc();
    Account findFirstByCreationTimeInMillisGreaterThanEqual(Long creationTime);
    Account findFirstByCreationTimeInMillisLessThanEqual(Long creationTime);

}
