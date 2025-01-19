package virtualstudygroup.backend.backend.ServicesTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        // Arrange: Create a new user
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPasswordHash("password123"); // This will be hashed in UserService

        // Act: Save the user
        User createdUser = userService.create(user);

        // Assert: Verify the user was saved correctly
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getName()).isEqualTo("Test User");
        assertThat(createdUser.getEmail()).isEqualTo("test@example.com");
        assertThat(createdUser.getPasswordHash()).isNotEqualTo("password123"); // Ensure it's hashed
    }
}
