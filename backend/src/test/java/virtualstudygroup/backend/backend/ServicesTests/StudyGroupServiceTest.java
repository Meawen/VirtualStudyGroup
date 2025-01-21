package virtualstudygroup.backend.backend.ServicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import virtualstudygroup.backend.backend.models.Membership;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.MembershipRepository;
import virtualstudygroup.backend.backend.repo.StudyGroupRepository;
import virtualstudygroup.backend.backend.service.StudyGroupService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudyGroupServiceTest {

    private StudyGroupRepository studyGroupRepository;
    private MembershipRepository membershipRepository;
    private StudyGroupService studyGroupService;

    @BeforeEach
    void setUp() {
        studyGroupRepository = mock(StudyGroupRepository.class);
        membershipRepository = mock(MembershipRepository.class);
        studyGroupService = new StudyGroupService(studyGroupRepository, membershipRepository);
    }

    @Test
    void testCreateStudyGroup() {
        // Arrange
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("Test Group");
        studyGroup.setDescription("Test Description");
        studyGroup.setVisibility("PUBLIC");
        when(studyGroupRepository.save(any(StudyGroup.class))).thenReturn(studyGroup);

        // Act
        StudyGroup createdGroup = studyGroupService.create(studyGroup);

        // Assert
        assertThat(createdGroup).isNotNull();
        assertThat(createdGroup.getName()).isEqualTo("Test Group");
        assertThat(createdGroup.getVisibility()).isEqualTo("PUBLIC");
        verify(studyGroupRepository, times(1)).save(studyGroup);
    }

    @Test
    void testFindById() {
        // Arrange
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setId(1);
        when(studyGroupRepository.findById(1)).thenReturn(Optional.of(studyGroup));

        // Act
        Optional<StudyGroup> foundGroup = studyGroupService.findById(1);

        // Assert
        assertThat(foundGroup).isPresent();
        assertThat(foundGroup.get().getId()).isEqualTo(1);
    }

    @Test
    void testUpdateStudyGroup() {
        // Arrange
        StudyGroup existingGroup = new StudyGroup();
        existingGroup.setId(1);
        existingGroup.setName("Original Name");

        StudyGroup updatedGroup = new StudyGroup();
        updatedGroup.setName("Updated Name");
        updatedGroup.setDescription("Updated Description");
        updatedGroup.setVisibility("PRIVATE");

        when(studyGroupRepository.findById(1)).thenReturn(Optional.of(existingGroup));
        when(studyGroupRepository.save(any(StudyGroup.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        StudyGroup result = studyGroupService.update(1, updatedGroup);

        // Assert
        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getDescription()).isEqualTo("Updated Description");
        assertThat(result.getVisibility()).isEqualTo("PRIVATE");
    }

    @Test
    void testDeleteById() {
        // Arrange
        when(studyGroupRepository.existsById(1)).thenReturn(true);

        // Act
        studyGroupService.deleteById(1);

        // Assert
        verify(studyGroupRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetGroupIfUserHasAccess_PublicGroup() {
        // Arrange
        StudyGroup publicGroup = new StudyGroup();
        publicGroup.setId(1);
        publicGroup.setVisibility("PUBLIC");

        when(studyGroupRepository.findById(1)).thenReturn(Optional.of(publicGroup));

        // Act
        Optional<StudyGroup> result = studyGroupService.getGroupIfUserHasAccess(1, 2);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getVisibility()).isEqualTo("PUBLIC");
    }

    @Test
    void testGetGroupIfUserHasAccess_PrivateGroup() {
        // Arrange
        User creator = new User();
        creator.setId(2);

        StudyGroup privateGroup = new StudyGroup();
        privateGroup.setId(1);
        privateGroup.setVisibility("PRIVATE");
        privateGroup.setCreatedBy(creator);

        Membership membership = new Membership();
        membership.setGroup(privateGroup);
        membership.setUser(creator);
        membership.setJoinedAt(Instant.now());

        when(studyGroupRepository.findById(1)).thenReturn(Optional.of(privateGroup));
        when(membershipRepository.findByUserIdAndGroupId(2, 1)).thenReturn(Optional.of(membership));

        // Act
        Optional<StudyGroup> result = studyGroupService.getGroupIfUserHasAccess(1, 2);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1);
    }

    @Test
    void testGetGroupIfUserHasAccess_NoAccess() {
        // Arrange
        User creator = new User();
        creator.setId(1);

        StudyGroup privateGroup = new StudyGroup();
        privateGroup.setId(1);
        privateGroup.setVisibility("PRIVATE");
        privateGroup.setCreatedBy(creator); // Set the creator

        when(studyGroupRepository.findById(1)).thenReturn(Optional.of(privateGroup));
        when(membershipRepository.findByUserIdAndGroupId(2, 1)).thenReturn(Optional.empty()); // User is not a member

        // Act
        Optional<StudyGroup> result = studyGroupService.getGroupIfUserHasAccess(1, 2);

        // Assert
        assertThat(result).isEmpty();
    }
}
