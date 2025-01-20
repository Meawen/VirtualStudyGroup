package virtualstudygroup.backend.backend.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import virtualstudygroup.backend.backend.controllers.GoalController;
import virtualstudygroup.backend.backend.models.Goal;
import virtualstudygroup.backend.backend.service.GoalService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class GoalControllerTest {

    @InjectMocks
    private GoalController goalController;

    @Mock
    private GoalService goalService;

    @Test
    public void testCreateGoal() {
        Goal goal = new Goal();
        goal.setId(1);
        goal.setTitle("Complete Java Course");

        Mockito.when(goalService.create(any(Goal.class))).thenReturn(goal);

        ResponseEntity<Goal> response = goalController.createGoal(goal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Complete Java Course", response.getBody().getTitle());
    }

    @Test
    public void testGetGoalById_Found() {
        Goal goal = new Goal();
        goal.setId(1);
        goal.setTitle("Read 5 Books");

        Mockito.when(goalService.findById(1)).thenReturn(Optional.of(goal));

        ResponseEntity<Goal> response = goalController.getGoalById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Read 5 Books", response.getBody().getTitle());
    }

    @Test
    public void testGetGoalById_NotFound() {
        Mockito.when(goalService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Goal> response = goalController.getGoalById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllGoals() {
        List<Goal> goals = List.of(new Goal(), new Goal());
        Mockito.when(goalService.findAll()).thenReturn(goals);

        ResponseEntity<List<Goal>> response = goalController.getAllGoals();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetAllGoalsByGroupId() {
        List<Goal> goals = List.of(new Goal(), new Goal());
        Mockito.when(goalService.findAllByGroupId(1)).thenReturn(goals);

        ResponseEntity<List<Goal>> response = goalController.getAllGoalsByGroupId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testUpdateGoal_Success() {
        Goal updatedGoal = new Goal();
        updatedGoal.setId(1);
        updatedGoal.setTitle("Updated Goal");

        Mockito.when(goalService.update(eq(1), any(Goal.class))).thenReturn(updatedGoal);

        ResponseEntity<Goal> response = goalController.updateGoal(1, updatedGoal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Goal", response.getBody().getTitle());
    }

    @Test
    public void testUpdateGoal_NotFound() {
        Mockito.when(goalService.update(eq(1), any(Goal.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<Goal> response = goalController.updateGoal(1, new Goal());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteGoalById_Success() {
        Mockito.doNothing().when(goalService).deleteById(1);

        ResponseEntity<Void> response = goalController.deleteGoalById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteGoalById_NotFound() {
        Mockito.doThrow(new IllegalArgumentException()).when(goalService).deleteById(1);

        ResponseEntity<Void> response = goalController.deleteGoalById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
