package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}