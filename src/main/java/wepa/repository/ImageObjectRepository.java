package wepa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.ImageObject;

public interface ImageObjectRepository extends JpaRepository<ImageObject, String>{
}
