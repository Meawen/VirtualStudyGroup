package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.Role;
import virtualstudygroup.backend.backend.repo.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Create a new Role
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    // Read a Role by ID
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    // Read all Roles
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    // Update a Role
    public Role update(Integer id, Role updatedRole) {
        return roleRepository.findById(id).map(role -> {
            role.setName(updatedRole.getName());
            return roleRepository.save(role);
        }).orElseThrow(() -> new IllegalArgumentException("Role with ID " + id + " not found"));
    }

    // Delete a Role by ID
    public void deleteById(Integer id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Role with ID " + id + " not found");
        }
    }
}
