package virtualstudygroup.backend.backend.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import virtualstudygroup.backend.backend.controllers.UserController;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setId(1);
        user.setName("John Doe");

        when(userService.create(any(User.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
    }

    @Test
    public void testGetUserById_Found() {
        User user = new User();
        user.setId(1);
        user.setName("Alice");

        when(userService.findById(1)).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Alice", response.getBody().getName());
    }

    @Test
    public void testGetUserById_NotFound() {
        when(userService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = List.of(new User(), new User());
        when(userService.findAll()).thenReturn(users);

        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testUpdateUser_Success() {
        User updatedUser = new User();
        updatedUser.setId(1);
        updatedUser.setName("Updated Name");

        when(userService.update(eq(1), any(User.class))).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser(1, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Name", response.getBody().getName());
    }

    @Test
    public void testUpdateUser_NotFound() {
        when(userService.update(eq(1), any(User.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<User> response = userController.updateUser(1, new User());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteUserById_Success() {
        doNothing().when(userService).deleteById(1);

        ResponseEntity<Void> response = userController.deleteUserById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteUserById_NotFound() {
        doThrow(new IllegalArgumentException()).when(userService).deleteById(1);

        ResponseEntity<Void> response = userController.deleteUserById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
