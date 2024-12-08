package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.Goal;
import virtualstudygroup.backend.backend.repo.GoalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    // Create a new Goal
    public Goal create(Goal goal) {
        return goalRepository.save(goal);
    }

    // Read a Goal by ID
    public Optional<Goal> findById(Integer id) {
        return goalRepository.findById(id);
    }

    // Read all Goals
    public List<Goal> findAll() {
        return goalRepository.findAll();
    }

    // Read all Goals by Group ID
    public List<Goal> findAllByGroupId(Integer groupId) {
        return goalRepository.findAllByGroupId(groupId);
    }

    // Update a Goal
    public Goal update(Integer id, Goal updatedGoal) {
        return goalRepository.findById(id).map(goal -> {
            goal.setTitle(updatedGoal.getTitle());
            goal.setDescription(updatedGoal.getDescription());
            goal.setStatus(updatedGoal.getStatus());
            goal.setUpdatedAt(updatedGoal.getUpdatedAt());
            return goalRepository.save(goal);
        }).orElseThrow(() -> new IllegalArgumentException("Goal with ID " + id + " not found"));
    }

    // Delete a Goal by ID
    public void deleteById(Integer id) {
        if (goalRepository.existsById(id)) {
            goalRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Goal with ID " + id + " not found");
        }
    }
}
