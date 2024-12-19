package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}