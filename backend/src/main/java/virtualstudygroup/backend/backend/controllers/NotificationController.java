package virtualstudygroup.backend.backend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import virtualstudygroup.backend.backend.models.Notification;
import virtualstudygroup.backend.backend.service.NotificationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Create a new Notification
    @PostMapping
    public ResponseEntity<Notification> createNotification(@Valid @RequestBody Notification notification) {
        Notification createdNotification = notificationService.create(notification);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    // Get a Notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) {
        Optional<Notification> notification = notificationService.findById(id);
        return notification.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get all Notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.findAll();
        return ResponseEntity.ok(notifications);
    }

    // Get all Notifications by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getAllNotificationsByUserId(@PathVariable Integer userId) {
        List<Notification> notifications = notificationService.findAllByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    // Update a Notification
    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Integer id, @Valid @RequestBody Notification updatedNotification) {
        try {
            Notification updated = notificationService.update(id, updatedNotification);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a Notification by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationById(@PathVariable Integer id) {
        try {
            notificationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
