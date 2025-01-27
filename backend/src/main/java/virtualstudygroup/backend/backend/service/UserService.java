package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.Role;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.RoleRepository;
import virtualstudygroup.backend.backend.repo.UserRepository;
import virtualstudygroup.backend.backend.utils.PasswordHashingUtility;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User create(User user) {
        user.setPasswordHash(PasswordHashingUtility.hashPassword(user.getPasswordHash()));
        if (user.getRole() == null) {
            Role defaultRole = roleRepository.findById(1)
                    .orElseThrow(() -> new RuntimeException("Default role not found"));
            user.setRole(defaultRole);
        }
        return userRepository.save(user);
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(Integer id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setPasswordHash(updatedUser.getPasswordHash());
            user.setProfilePicture(updatedUser.getProfilePicture());
            user.setUpdatedAt(updatedUser.getUpdatedAt());
            user.setRole(updatedUser.getRole());
            return userRepository.save(user);
        }).orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found"));
    }

    // Delete a User by ID
    public void deleteById(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
    }


}
