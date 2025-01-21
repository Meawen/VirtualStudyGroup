package virtualstudygroup.backend.backend.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import virtualstudygroup.backend.backend.controllers.NotificationController;
import virtualstudygroup.backend.backend.models.Notification;
import virtualstudygroup.backend.backend.service.NotificationService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService notificationService;

    @Test
    public void testCreateNotification() {
        Notification notification = new Notification();
        notification.setId(1);
        notification.setMessage("New Notification");

        when(notificationService.create(any(Notification.class))).thenReturn(notification);

        ResponseEntity<Notification> response = notificationController.createNotification(notification);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Notification", response.getBody().getMessage());
    }

    @Test
    public void testGetNotificationById_Found() {
        Notification notification = new Notification();
        notification.setId(1);
        notification.setMessage("Reminder");

        when(notificationService.findById(1)).thenReturn(Optional.of(notification));

        ResponseEntity<Notification> response = notificationController.getNotificationById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Reminder", response.getBody().getMessage());
    }

    @Test
    public void testGetNotificationById_NotFound() {
        when(notificationService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Notification> response = notificationController.getNotificationById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllNotifications() {
        List<Notification> notifications = List.of(new Notification(), new Notification());
        when(notificationService.findAll()).thenReturn(notifications);

        ResponseEntity<List<Notification>> response = notificationController.getAllNotifications();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetAllNotificationsByUserId() {
        List<Notification> notifications = List.of(new Notification(), new Notification());
        when(notificationService.findAllByUserId(1)).thenReturn(notifications);

        ResponseEntity<List<Notification>> response = notificationController.getAllNotificationsByUserId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testUpdateNotification_Success() {
        Notification updatedNotification = new Notification();
        updatedNotification.setId(1);
        updatedNotification.setMessage("Updated Notification");

        when(notificationService.update(eq(1), any(Notification.class))).thenReturn(updatedNotification);

        ResponseEntity<Notification> response = notificationController.updateNotification(1, updatedNotification);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Notification", response.getBody().getMessage());
    }

    @Test
    public void testUpdateNotification_NotFound() {
        when(notificationService.update(eq(1), any(Notification.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<Notification> response = notificationController.updateNotification(1, new Notification());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteNotificationById_Success() {
        doNothing().when(notificationService).deleteById(1);

        ResponseEntity<Void> response = notificationController.deleteNotificationById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(notificationService, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteNotificationById_NotFound() {
        doThrow(new IllegalArgumentException()).when(notificationService).deleteById(1);

        ResponseEntity<Void> response = notificationController.deleteNotificationById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
