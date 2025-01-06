package virtualstudygroup.backend.backend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import virtualstudygroup.backend.backend.models.Goal;
import virtualstudygroup.backend.backend.service.GoalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    
    @PostMapping
    public ResponseEntity<Goal> createGoal(@Valid @RequestBody Goal goal) {
        Goal createdGoal = goalService.create(goal);
        return new ResponseEntity<>(createdGoal, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Integer id) {
        Optional<Goal> goal = goalService.findById(id);
        return goal.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @GetMapping
    public ResponseEntity<List<Goal>> getAllGoals() {
        List<Goal> goals = goalService.findAll();
        return ResponseEntity.ok(goals);
    }


    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Goal>> getAllGoalsByGroupId(@PathVariable Integer groupId) {
        List<Goal> goals = goalService.findAllByGroupId(groupId);
        return ResponseEntity.ok(goals);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Integer id, @Valid @RequestBody Goal updatedGoal) {
        try {
            Goal updated = goalService.update(id, updatedGoal);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoalById(@PathVariable Integer id) {
        try {
            goalService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
