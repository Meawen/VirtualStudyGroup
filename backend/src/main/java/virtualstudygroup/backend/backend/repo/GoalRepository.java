package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.Goal;

public interface GoalRepository extends JpaRepository<Goal, Integer> {
}