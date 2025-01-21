package virtualstudygroup.backend.backend.RepoTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.Role;
import virtualstudygroup.backend.backend.repo.RoleRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAndFindById() {
        // Mock podaci
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        // Mock ponašanje
        when(roleRepository.save(role)).thenReturn(role);
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        // Test - save
        Role savedRole = roleRepository.save(role);
        assertNotNull(savedRole);
        assertEquals("ADMIN", savedRole.getName());

        // Test - findById
        Optional<Role> retrievedRole = roleRepository.findById(1);
        assertTrue(retrievedRole.isPresent());
        assertEquals("ADMIN", retrievedRole.get().getName());

        // Verifikacija
        verify(roleRepository, times(1)).save(role);
        verify(roleRepository, times(1)).findById(1);
    }

    @Test
    void testFindByIdNotFound() {
        // Mock ponašanje
        when(roleRepository.findById(2)).thenReturn(Optional.empty());

        // Test
        Optional<Role> retrievedRole = roleRepository.findById(2);
        assertFalse(retrievedRole.isPresent());

        // Verifikacija
        verify(roleRepository, times(1)).findById(2);
    }
}
