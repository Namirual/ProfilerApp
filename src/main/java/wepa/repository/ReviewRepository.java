package wepa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import wepa.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
