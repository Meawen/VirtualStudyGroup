package virtualstudygroup.backend.backend.ServicesTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.Role;
import virtualstudygroup.backend.backend.repo.RoleRepository;
import virtualstudygroup.backend.backend.service.RoleService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    public RoleServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRole() {
        // Arrange
        Role role = new Role();
        role.setName("Admin");
        when(roleRepository.save(role)).thenReturn(role);

        // Act
        Role createdRole = roleService.create(role);

        // Assert
        assertThat(createdRole).isNotNull();
        assertThat(createdRole.getName()).isEqualTo("Admin");
        verify(roleRepository, times(1)).save(role);
    }

    @Test
    public void testFindRoleById() {
        // Arrange
        Role role = new Role();
        role.setId(1);
        role.setName("User");
        when(roleRepository.findById(1)).thenReturn(Optional.of(role));

        // Act
        Optional<Role> foundRole = roleService.findById(1);

        // Assert
        assertThat(foundRole).isPresent();
        assertThat(foundRole.get().getName()).isEqualTo("User");
        verify(roleRepository, times(1)).findById(1);
    }

    @Test
    public void testFindAllRoles() {
        // Arrange
        Role role1 = new Role();
        role1.setName("Admin");
        Role role2 = new Role();
        role2.setName("User");
        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        // Act
        List<Role> roles = roleService.findAll();

        // Assert
        assertThat(roles).hasSize(2);
        assertThat(roles.get(0).getName()).isEqualTo("Admin");
        assertThat(roles.get(1).getName()).isEqualTo("User");
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateRole() {
        // Arrange
        Role existingRole = new Role();
        existingRole.setId(1);
        existingRole.setName("Old Name");

        Role updatedRole = new Role();
        updatedRole.setName("New Name");

        when(roleRepository.findById(1)).thenReturn(Optional.of(existingRole));
        when(roleRepository.save(existingRole)).thenReturn(existingRole);

        // Act
        Role result = roleService.update(1, updatedRole);

        // Assert
        assertThat(result.getName()).isEqualTo("New Name");
        verify(roleRepository, times(1)).findById(1);
        verify(roleRepository, times(1)).save(existingRole);
    }

    @Test
    public void testUpdateRole_NotFound() {
        // Arrange
        Role updatedRole = new Role();
        updatedRole.setName("New Name");
        when(roleRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> roleService.update(1, updatedRole));
        verify(roleRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteRoleById() {
        // Arrange
        when(roleRepository.existsById(1)).thenReturn(true);

        // Act
        roleService.deleteById(1);

        // Assert
        verify(roleRepository, times(1)).existsById(1);
        verify(roleRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteRoleById_NotFound() {
        // Arrange
        when(roleRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> roleService.deleteById(1));
        verify(roleRepository, times(1)).existsById(1);
    }
}
