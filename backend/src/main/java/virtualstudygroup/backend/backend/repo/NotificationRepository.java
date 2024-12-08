package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findAllByUserId(Integer userId);
}