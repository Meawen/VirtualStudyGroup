package virtualstudygroup.backend.backend.RepoTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.StudyGroupRepository;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudyGroupRepositoryTest {

    @Mock
    private StudyGroupRepository studyGroupRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllByVisibility() {
        // Mock podaci
        StudyGroup group1 = new StudyGroup();
        group1.setId(1);
        group1.setName("Public Group 1");
        group1.setVisibility("PUBLIC");

        StudyGroup group2 = new StudyGroup();
        group2.setId(2);
        group2.setName("Public Group 2");
        group2.setVisibility("PUBLIC");

        when(studyGroupRepository.findAllByVisibility("PUBLIC"))
                .thenReturn(Arrays.asList(group1, group2));

        // Test
        List<StudyGroup> groups = studyGroupRepository.findAllByVisibility("PUBLIC");
        assertEquals(2, groups.size());
        assertEquals("Public Group 1", groups.get(0).getName());
        assertEquals("Public Group 2", groups.get(1).getName());

        // Verifikacija
        verify(studyGroupRepository, times(1)).findAllByVisibility("PUBLIC");
    }

    @Test
    void testFindAllByVisibilityAndCreatedById() {
        // Mock podaci
        User user = new User();
        user.setId(1);

        StudyGroup group1 = new StudyGroup();
        group1.setId(1);
        group1.setName("User's Private Group");
        group1.setVisibility("PRIVATE");
        group1.setCreatedBy(user);

        when(studyGroupRepository.findAllByVisibilityAndCreatedById("PRIVATE", 1))
                .thenReturn(List.of(group1));

        // Test
        List<StudyGroup> groups = studyGroupRepository.findAllByVisibilityAndCreatedById("PRIVATE", 1);
        assertEquals(1, groups.size());
        assertEquals("User's Private Group", groups.get(0).getName());
        assertEquals(1, groups.get(0).getCreatedBy().getId());

        // Verifikacija
        verify(studyGroupRepository, times(1))
                .findAllByVisibilityAndCreatedById("PRIVATE", 1);
    }

    @Test
    void testSaveAndFindById() {
        // Mock podaci
        StudyGroup group = new StudyGroup();
        group.setId(1);
        group.setName("Test Group");
        group.setVisibility("PUBLIC");
        group.setCreatedAt(Instant.now());
        group.setUpdatedAt(Instant.now());

        when(studyGroupRepository.save(group)).thenReturn(group);
        when(studyGroupRepository.findById(1)).thenReturn(Optional.of(group));

        // Test - save
        StudyGroup savedGroup = studyGroupRepository.save(group);
        assertNotNull(savedGroup);
        assertEquals("Test Group", savedGroup.getName());

        // Test - findById
        Optional<StudyGroup> retrievedGroup = studyGroupRepository.findById(1);
        assertTrue(retrievedGroup.isPresent());
        assertEquals("Test Group", retrievedGroup.get().getName());

        // Verifikacija
        verify(studyGroupRepository, times(1)).save(group);
        verify(studyGroupRepository, times(1)).findById(1);
    }
}
