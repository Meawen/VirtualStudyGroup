package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.VirtualRoom;

public interface VirtualRoomRepository extends JpaRepository<VirtualRoom, Integer> {
}