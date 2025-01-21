package virtualstudygroup.backend.backend.ServicesTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.Notification;
import virtualstudygroup.backend.backend.repo.NotificationRepository;
import virtualstudygroup.backend.backend.service.NotificationService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    public NotificationServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNotification() {
        // Arrange
        Notification notification = new Notification();
        notification.setMessage("New Notification");
        notification.setStatus("UNREAD");
        when(notificationRepository.save(notification)).thenReturn(notification);

        // Act
        Notification createdNotification = notificationService.create(notification);

        // Assert
        assertThat(createdNotification).isNotNull();
        assertThat(createdNotification.getMessage()).isEqualTo("New Notification");
        assertThat(createdNotification.getStatus()).isEqualTo("UNREAD");
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    public void testFindNotificationById() {
        // Arrange
        Notification notification = new Notification();
        notification.setId(1);
        notification.setMessage("Sample Notification");
        when(notificationRepository.findById(1)).thenReturn(Optional.of(notification));

        // Act
        Optional<Notification> foundNotification = notificationService.findById(1);

        // Assert
        assertThat(foundNotification).isPresent();
        assertThat(foundNotification.get().getMessage()).isEqualTo("Sample Notification");
        verify(notificationRepository, times(1)).findById(1);
    }

    @Test
    public void testFindAllNotifications() {
        // Arrange
        Notification notification1 = new Notification();
        notification1.setMessage("Notification 1");
        Notification notification2 = new Notification();
        notification2.setMessage("Notification 2");
        when(notificationRepository.findAll()).thenReturn(Arrays.asList(notification1, notification2));

        // Act
        List<Notification> notifications = notificationService.findAll();

        // Assert
        assertThat(notifications).hasSize(2);
        assertThat(notifications.get(0).getMessage()).isEqualTo("Notification 1");
        assertThat(notifications.get(1).getMessage()).isEqualTo("Notification 2");
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllNotificationsByUserId() {
        // Arrange
        Notification notification1 = new Notification();
        notification1.setMessage("Notification 1");
        Notification notification2 = new Notification();
        notification2.setMessage("Notification 2");
        when(notificationRepository.findAllByUserId(1)).thenReturn(Arrays.asList(notification1, notification2));

        // Act
        List<Notification> notifications = notificationService.findAllByUserId(1);

        // Assert
        assertThat(notifications).hasSize(2);
        verify(notificationRepository, times(1)).findAllByUserId(1);
    }

    @Test
    public void testUpdateNotification() {
        // Arrange
        Notification existingNotification = new Notification();
        existingNotification.setId(1);
        existingNotification.setMessage("Old Message");
        existingNotification.setStatus("UNREAD");

        Notification updatedNotification = new Notification();
        updatedNotification.setMessage("Updated Message");
        updatedNotification.setStatus("READ");

        when(notificationRepository.findById(1)).thenReturn(Optional.of(existingNotification));
        when(notificationRepository.save(existingNotification)).thenReturn(existingNotification);

        // Act
        Notification result = notificationService.update(1, updatedNotification);

        // Assert
        assertThat(result.getMessage()).isEqualTo("Updated Message");
        assertThat(result.getStatus()).isEqualTo("READ");
        verify(notificationRepository, times(1)).findById(1);
        verify(notificationRepository, times(1)).save(existingNotification);
    }

    @Test
    public void testUpdateNotification_NotFound() {
        // Arrange
        Notification updatedNotification = new Notification();
        updatedNotification.setMessage("Updated Message");
        when(notificationRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> notificationService.update(1, updatedNotification));
        verify(notificationRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteNotificationById() {
        // Arrange
        when(notificationRepository.existsById(1)).thenReturn(true);

        // Act
        notificationService.deleteById(1);

        // Assert
        verify(notificationRepository, times(1)).existsById(1);
        verify(notificationRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteNotificationById_NotFound() {
        // Arrange
        when(notificationRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> notificationService.deleteById(1));
        verify(notificationRepository, times(1)).existsById(1);
    }
}
