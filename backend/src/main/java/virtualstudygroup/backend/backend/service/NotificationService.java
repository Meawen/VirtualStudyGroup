package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.Notification;
import virtualstudygroup.backend.backend.repo.NotificationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification create(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Optional<Notification> findById(Integer id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public List<Notification> findAllByUserId(Integer userId) {
        return notificationRepository.findAllByUserId(userId);
    }

    public Notification update(Integer id, Notification updatedNotification) {
        return notificationRepository.findById(id).map(notification -> {
            notification.setMessage(updatedNotification.getMessage());
            notification.setStatus(updatedNotification.getStatus());
            notification.setUpdatedAt(updatedNotification.getUpdatedAt());
            return notificationRepository.save(notification);
        }).orElseThrow(() -> new IllegalArgumentException("Notification with ID " + id + " not found"));
    }

    public void deleteById(Integer id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Notification with ID " + id + " not found");
        }
    }
}
