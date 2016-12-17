package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.Account;
import wepa.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, String>{
    public Profile findByOwnerAccount(Account ownerAccount);
}
