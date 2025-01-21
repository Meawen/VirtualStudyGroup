package virtualstudygroup.backend.backend.ServicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.Role;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.RoleRepository;
import virtualstudygroup.backend.backend.repo.UserRepository;
import virtualstudygroup.backend.backend.service.UserService;
import virtualstudygroup.backend.backend.utils.PasswordHashingUtility;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser_WithDefaultRole() {
        // Arrange
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPasswordHash("password123");

        Role defaultRole = new Role();
        defaultRole.setId(1);
        defaultRole.setName("USER");

        when(roleRepository.findById(1)).thenReturn(Optional.of(defaultRole));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User createdUser = userService.create(user);

        // Assert
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getRole()).isEqualTo(defaultRole);
        assertThat(createdUser.getPasswordHash()).isNotEqualTo("password123"); // Password should be hashed
        verify(userRepository, times(1)).save(user);
        verify(roleRepository, times(1)).findById(1);
    }

    @Test
    public void testCreateUser_NoDefaultRoleThrowsException() {
        // Arrange
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPasswordHash("password123");

        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.create(user));
        assertThat(exception.getMessage()).isEqualTo("Default role not found");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testFindById_UserExists() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setName("Test User");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Act
        Optional<User> foundUser = userService.findById(1);

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Test User");
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testFindById_UserDoesNotExist() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<User> foundUser = userService.findById(1);

        // Assert
        assertThat(foundUser).isEmpty();
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testUpdateUser_Success() {
        // Arrange
        User existingUser = new User();
        existingUser.setId(1);
        existingUser.setName("Old Name");

        User updatedUser = new User();
        updatedUser.setName("New Name");
        updatedUser.setEmail("new@example.com");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User result = userService.update(1, updatedUser);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("New Name");
        assertThat(result.getEmail()).isEqualTo("new@example.com");
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.update(1, new User()));
        assertThat(exception.getMessage()).isEqualTo("User with ID 1 not found");
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testDeleteById_Success() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(true);

        // Act
        userService.deleteById(1);

        // Assert
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteById_UserNotFound() {
        // Arrange
        when(userRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteById(1));
        assertThat(exception.getMessage()).isEqualTo("User with ID 1 not found");
        verify(userRepository, times(1)).existsById(1);
        verify(userRepository, never()).deleteById(anyInt());
    }
}
