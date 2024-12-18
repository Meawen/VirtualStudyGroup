package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a new User
    public User create(User user) {
        return userRepository.save(user);
    }

    // Read a User by ID
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    // Read all Users
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Update a User
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
