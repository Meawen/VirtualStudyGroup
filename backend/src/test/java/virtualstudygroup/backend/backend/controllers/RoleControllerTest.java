package virtualstudygroup.backend.backend.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import virtualstudygroup.backend.backend.controllers.RoleController;
import virtualstudygroup.backend.backend.models.Role;
import virtualstudygroup.backend.backend.service.RoleService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @InjectMocks
    private RoleController roleController;

    @Mock
    private RoleService roleService;

    @Test
    public void testCreateRole() {
        Role role = new Role();
        role.setId(1);
        role.setName("Admin");

        when(roleService.create(any(Role.class))).thenReturn(role);

        ResponseEntity<Role> response = roleController.createRole(role);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Admin", response.getBody().getName());
    }

    @Test
    public void testGetRoleById_Found() {
        Role role = new Role();
        role.setId(1);
        role.setName("User");

        when(roleService.findById(1)).thenReturn(Optional.of(role));

        ResponseEntity<Role> response = roleController.getRoleById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("User", response.getBody().getName());
    }

    @Test
    public void testGetRoleById_NotFound() {
        when(roleService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Role> response = roleController.getRoleById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllRoles() {
        List<Role> roles = List.of(new Role(), new Role());
        when(roleService.findAll()).thenReturn(roles);

        ResponseEntity<List<Role>> response = roleController.getAllRoles();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testUpdateRole_Success() {
        Role updatedRole = new Role();
        updatedRole.setId(1);
        updatedRole.setName("Moderator");

        when(roleService.update(eq(1), any(Role.class))).thenReturn(updatedRole);

        ResponseEntity<Role> response = roleController.updateRole(1, updatedRole);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Moderator", response.getBody().getName());
    }

    @Test
    public void testUpdateRole_NotFound() {
        when(roleService.update(eq(1), any(Role.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<Role> response = roleController.updateRole(1, new Role());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteRoleById_Success() {
        doNothing().when(roleService).deleteById(1);

        ResponseEntity<Void> response = roleController.deleteRoleById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(roleService, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteRoleById_NotFound() {
        doThrow(new IllegalArgumentException()).when(roleService).deleteById(1);

        ResponseEntity<Void> response = roleController.deleteRoleById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
