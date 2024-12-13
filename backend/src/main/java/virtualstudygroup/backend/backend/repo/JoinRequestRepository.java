package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.Goal;
import virtualstudygroup.backend.backend.models.JoinRequest;

import java.util.List;


public interface JoinRequestRepository extends JpaRepository<JoinRequest, Integer> {
    List<JoinRequest> findAllByGroupId(Integer groupId);
    List<JoinRequest> findAllByUserId(Integer userId);
}