package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.Goal;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    List<Goal> findAllByGroupId(Integer groupId);
}