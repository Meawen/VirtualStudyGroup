package virtualstudygroup.backend.backend.ServicesTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.Goal;
import virtualstudygroup.backend.backend.repo.GoalRepository;
import virtualstudygroup.backend.backend.service.GoalService;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalService goalService;

    public GoalServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateGoal() {
        // Arrange
        Goal goal = new Goal();
        goal.setTitle("Complete Assignment");
        goal.setDescription("Finish the assignment for the study group.");
        when(goalRepository.save(goal)).thenReturn(goal);

        // Act
        Goal createdGoal = goalService.create(goal);

        // Assert
        assertThat(createdGoal).isNotNull();
        assertThat(createdGoal.getTitle()).isEqualTo("Complete Assignment");
        verify(goalRepository, times(1)).save(goal);
    }

    @Test
    public void testFindById() {
        // Arrange
        Goal goal = new Goal();
        goal.setId(1);
        when(goalRepository.findById(1)).thenReturn(Optional.of(goal));

        // Act
        Optional<Goal> foundGoal = goalService.findById(1);

        // Assert
        assertThat(foundGoal).isPresent();
        assertThat(foundGoal.get().getId()).isEqualTo(1);
        verify(goalRepository, times(1)).findById(1);
    }

    @Test
    public void testFindAll() {
        // Arrange
        Goal goal1 = new Goal();
        Goal goal2 = new Goal();
        when(goalRepository.findAll()).thenReturn(Arrays.asList(goal1, goal2));

        // Act
        List<Goal> goals = goalService.findAll();

        // Assert
        assertThat(goals).hasSize(2);
        verify(goalRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllByGroupId() {
        // Arrange
        Goal goal1 = new Goal();
        Goal goal2 = new Goal();
        when(goalRepository.findAllByGroupId(1)).thenReturn(Arrays.asList(goal1, goal2));

        // Act
        List<Goal> goals = goalService.findAllByGroupId(1);

        // Assert
        assertThat(goals).hasSize(2);
        verify(goalRepository, times(1)).findAllByGroupId(1);
    }

    @Test
    public void testUpdateGoal() {
        // Arrange
        Goal existingGoal = new Goal();
        existingGoal.setId(1);
        existingGoal.setTitle("Old Title");

        Goal updatedGoal = new Goal();
        updatedGoal.setTitle("New Title");
        updatedGoal.setDescription("Updated description.");
        updatedGoal.setStatus("In Progress");
        updatedGoal.setUpdatedAt(Instant.now());

        when(goalRepository.findById(1)).thenReturn(Optional.of(existingGoal));
        when(goalRepository.save(existingGoal)).thenReturn(existingGoal);

        // Act
        Goal result = goalService.update(1, updatedGoal);

        // Assert
        assertThat(result.getTitle()).isEqualTo("New Title");
        assertThat(result.getDescription()).isEqualTo("Updated description.");
        assertThat(result.getStatus()).isEqualTo("In Progress");
        verify(goalRepository, times(1)).findById(1);
        verify(goalRepository, times(1)).save(existingGoal);
    }

    @Test
    public void testUpdateGoal_NotFound() {
        // Arrange
        when(goalRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> goalService.update(1, new Goal()));
        verify(goalRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteById() {
        // Arrange
        when(goalRepository.existsById(1)).thenReturn(true);

        // Act
        goalService.deleteById(1);

        // Assert
        verify(goalRepository, times(1)).existsById(1);
        verify(goalRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteById_NotFound() {
        // Arrange
        when(goalRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> goalService.deleteById(1));
        verify(goalRepository, times(1)).existsById(1);
        verify(goalRepository, never()).deleteById(anyInt());
    }
}
