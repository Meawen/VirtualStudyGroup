package virtualstudygroup.backend.backend.RepoTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.Membership;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.MembershipRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembershipRepositoryTest {

    @Mock
    private MembershipRepository membershipRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMembership() {
        // Mock User
        User user = new User();
        user.setId(1);
        user.setName("Test User");

        // Mock StudyGroup
        StudyGroup group = new StudyGroup();
        group.setId(1);
        group.setName("Test Group");

        // Mock Membership
        Membership membership = new Membership();
        membership.setId(1);
        membership.setUser(user);
        membership.setGroup(group);
        membership.setJoinedAt(Instant.now());

        // Mock ponašanje
        when(membershipRepository.save(membership)).thenReturn(membership);

        // Test - save
        Membership savedMembership = membershipRepository.save(membership);
        assertNotNull(savedMembership);
        assertEquals(user, savedMembership.getUser());
        assertEquals(group, savedMembership.getGroup());

        // Verifikacija
        verify(membershipRepository, times(1)).save(membership);
    }

    @Test
    void testFindByUserIdAndGroupId() {
        // Mock User
        User user = new User();
        user.setId(1);

        // Mock StudyGroup
        StudyGroup group = new StudyGroup();
        group.setId(2);

        // Mock Membership
        Membership membership = new Membership();
        membership.setId(1);
        membership.setUser(user);
        membership.setGroup(group);
        membership.setJoinedAt(Instant.now());

        // Mock ponašanje
        when(membershipRepository.findByUserIdAndGroupId(1, 2)).thenReturn(Optional.of(membership));

        // Test - findByUserIdAndGroupId
        Optional<Membership> retrievedMembership = membershipRepository.findByUserIdAndGroupId(1, 2);
        assertTrue(retrievedMembership.isPresent());
        assertEquals(user, retrievedMembership.get().getUser());
        assertEquals(group, retrievedMembership.get().getGroup());

        // Verifikacija
        verify(membershipRepository, times(1)).findByUserIdAndGroupId(1, 2);
    }

    @Test
    void testFindByUserIdAndGroupId_NotFound() {
        // Mock ponašanje
        when(membershipRepository.findByUserIdAndGroupId(1, 2)).thenReturn(Optional.empty());

        // Test - not found
        Optional<Membership> retrievedMembership = membershipRepository.findByUserIdAndGroupId(1, 2);
        assertFalse(retrievedMembership.isPresent());

        // Verifikacija
        verify(membershipRepository, times(1)).findByUserIdAndGroupId(1, 2);
    }

    @Test
    void testDeleteMembership() {
        // Mock Membership
        Membership membership = new Membership();
        membership.setId(1);

        // Test - delete
        doNothing().when(membershipRepository).delete(membership);

        membershipRepository.delete(membership);

        // Verifikacija
        verify(membershipRepository, times(1)).delete(membership);
    }

    @Test
    void testFindAllMemberships() {
        // Mock Memberships
        Membership membership1 = new Membership();
        membership1.setId(1);

        Membership membership2 = new Membership();
        membership2.setId(2);

        List<Membership> memberships = new ArrayList<>();
        memberships.add(membership1);
        memberships.add(membership2);

        // Mock ponašanje
        when(membershipRepository.findAll()).thenReturn(memberships);

        // Test - findAll
        List<Membership> retrievedMemberships = membershipRepository.findAll();
        assertNotNull(retrievedMemberships);
        assertEquals(2, retrievedMemberships.size());

        // Verifikacija
        verify(membershipRepository, times(1)).findAll();
    }
}
