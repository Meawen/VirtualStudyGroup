package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}