package virtualstudygroup.backend.backend.RepoTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.Notification;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.NotificationRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationRepositoryTest {

    @Mock
    private NotificationRepository notificationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveNotification() {
        // Mock user
        User user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setEmail("test@example.com");

        // Mock notification
        Notification notification = new Notification();
        notification.setId(1);
        notification.setUser(user);
        notification.setMessage("Test Notification");
        notification.setStatus("UNREAD");
        notification.setCreatedAt(Instant.now());
        notification.setUpdatedAt(Instant.now());

        // Mock ponašanje
        when(notificationRepository.save(notification)).thenReturn(notification);

        // Test - save
        Notification savedNotification = notificationRepository.save(notification);
        assertNotNull(savedNotification);
        assertEquals("Test Notification", savedNotification.getMessage());
        assertEquals("UNREAD", savedNotification.getStatus());
        assertEquals(user, savedNotification.getUser());

        // Verifikacija
        verify(notificationRepository, times(1)).save(notification);
    }

    @Test
    void testFindAllByUserId() {
        // Mock user
        User user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setEmail("test@example.com");

        // Mock notifications
        Notification notification1 = new Notification();
        notification1.setId(1);
        notification1.setUser(user);
        notification1.setMessage("Notification 1");
        notification1.setStatus("UNREAD");
        notification1.setCreatedAt(Instant.now());

        Notification notification2 = new Notification();
        notification2.setId(2);
        notification2.setUser(user);
        notification2.setMessage("Notification 2");
        notification2.setStatus("READ");
        notification2.setCreatedAt(Instant.now());

        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification1);
        notifications.add(notification2);

        // Mock ponašanje
        when(notificationRepository.findAllByUserId(user.getId())).thenReturn(notifications);

        // Test - findAllByUserId
        List<Notification> retrievedNotifications = notificationRepository.findAllByUserId(user.getId());
        assertNotNull(retrievedNotifications);
        assertEquals(2, retrievedNotifications.size());
        assertEquals("Notification 1", retrievedNotifications.get(0).getMessage());
        assertEquals("Notification 2", retrievedNotifications.get(1).getMessage());

        // Verifikacija
        verify(notificationRepository, times(1)).findAllByUserId(user.getId());
    }

    @Test
    void testFindAllByUserIdNoResults() {
        // Mock ponašanje
        when(notificationRepository.findAllByUserId(999)).thenReturn(new ArrayList<>());

        // Test - no results
        List<Notification> retrievedNotifications = notificationRepository.findAllByUserId(999);
        assertNotNull(retrievedNotifications);
        assertTrue(retrievedNotifications.isEmpty());

        // Verifikacija
        verify(notificationRepository, times(1)).findAllByUserId(999);
    }
}
