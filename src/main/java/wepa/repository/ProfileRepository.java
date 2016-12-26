package wepa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.Account;
import wepa.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String>{
    
    List<Profile> findByOwnerAccount(Account ownerAccount);
    
    Profile findFirstByOrderByCreationTimeInMillisDesc();
    
    Profile findFirstByCreationTimeInMillisGreaterThan(Long creationTime);
    
    Profile findFirstByCreationTimeInMillisLessThan(Long creationTime);
    
}
