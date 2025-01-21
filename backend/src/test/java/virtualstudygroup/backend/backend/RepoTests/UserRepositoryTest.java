package virtualstudygroup.backend.backend.RepoTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import virtualstudygroup.backend.backend.models.Role;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.RoleRepository;
import virtualstudygroup.backend.backend.repo.UserRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Test
    void testFindByEmail() {
        // Kreiraj i spremi User
        User user = new User();
        user.setName("Email Test User");
        user.setEmail("emailtest@example.com");
        user.setPasswordHash("hashedpassword");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(Instant.now());
        user = userRepository.save(user);

        // Dohvati User prema emailu
        Optional<User> retrieved = userRepository.findByEmail("emailtest@example.com");
        assertTrue(retrieved.isPresent());
        assertEquals("Email Test User", retrieved.get().getName());
        assertEquals("emailtest@example.com", retrieved.get().getEmail());
    }

    @Test
    void testDelete() {
        // Kreiraj i spremi User
        User user = new User();
        user.setName("Delete Test User");
        user.setEmail("deletetest@example.com");
        user.setPasswordHash("hashedpassword");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(Instant.now());
        user = userRepository.save(user);

        // Obriši User
        userRepository.deleteById(user.getId());

        // Provjeri da je obrisan
        Optional<User> retrieved = userRepository.findById(user.getId());
        assertFalse(retrieved.isPresent());
    }
}
