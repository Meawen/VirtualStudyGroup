package virtualstudygroup.backend.backend.RepoTests;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import virtualstudygroup.backend.backend.models.Goal;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.repo.GoalRepository;
import virtualstudygroup.backend.backend.repo.StudyGroupRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GoalRepositoryTest {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Test
    void testFindAllByGroupId() {
        // Kreiraj i sačuvaj StudyGroup
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("Test Group");
        studyGroup.setVisibility("PUBLIC");
        studyGroup = studyGroupRepository.save(studyGroup);

        // Kreiraj i sačuvaj Goal
        Goal goal = new Goal();
        goal.setTitle("Test Goal");
        goal.setGroup(studyGroup);
        goalRepository.save(goal);

        // Dohvati rezultate
        List<Goal> goals = goalRepository.findAllByGroupId(studyGroup.getId());
        assertEquals(1, goals.size());
        assertEquals("Test Goal", goals.get(0).getTitle());
    }
}
