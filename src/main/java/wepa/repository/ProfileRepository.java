package wepa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.Account;
import wepa.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long>{
    
    List<Profile> findByOwnerAccount(Account ownerAccount);
    
    Profile findFirstByIdGreaterThan(Long id);
    
    Profile findFirstByIdLessThan(Long id);
    
}
