package virtualstudygroup.backend.backend.RepoTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.JoinRequest;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.JoinRequestRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JoinRequestRepositoryTest {

    @Mock
    private JoinRequestRepository joinRequestRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveJoinRequest() {
        // Mock User
        User user = new User();
        user.setId(1);
        user.setName("Test User");

        // Mock StudyGroup
        StudyGroup group = new StudyGroup();
        group.setId(1);
        group.setName("Test Group");

        // Mock JoinRequest
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setId(1);
        joinRequest.setUser(user);
        joinRequest.setGroup(group);
        joinRequest.setStatus("PENDING");
        joinRequest.setCreatedAt(Instant.now());

        // Mock ponašanje
        when(joinRequestRepository.save(joinRequest)).thenReturn(joinRequest);

        // Test - save
        JoinRequest savedJoinRequest = joinRequestRepository.save(joinRequest);
        assertNotNull(savedJoinRequest);
        assertEquals(user, savedJoinRequest.getUser());
        assertEquals(group, savedJoinRequest.getGroup());
        assertEquals("PENDING", savedJoinRequest.getStatus());

        // Verifikacija
        verify(joinRequestRepository, times(1)).save(joinRequest);
    }

    @Test
    void testFindAllByGroupId() {
        // Mock StudyGroup
        StudyGroup group = new StudyGroup();
        group.setId(1);

        // Mock JoinRequests
        JoinRequest joinRequest1 = new JoinRequest();
        joinRequest1.setId(1);
        joinRequest1.setGroup(group);

        JoinRequest joinRequest2 = new JoinRequest();
        joinRequest2.setId(2);
        joinRequest2.setGroup(group);

        List<JoinRequest> joinRequests = new ArrayList<>();
        joinRequests.add(joinRequest1);
        joinRequests.add(joinRequest2);

        // Mock ponašanje
        when(joinRequestRepository.findAllByGroupId(1)).thenReturn(joinRequests);

        // Test - findAllByGroupId
        List<JoinRequest> retrievedRequests = joinRequestRepository.findAllByGroupId(1);
        assertNotNull(retrievedRequests);
        assertEquals(2, retrievedRequests.size());

        // Verifikacija
        verify(joinRequestRepository, times(1)).findAllByGroupId(1);
    }

    @Test
    void testFindAllByUserId() {
        // Mock User
        User user = new User();
        user.setId(1);

        // Mock JoinRequests
        JoinRequest joinRequest1 = new JoinRequest();
        joinRequest1.setId(1);
        joinRequest1.setUser(user);

        JoinRequest joinRequest2 = new JoinRequest();
        joinRequest2.setId(2);
        joinRequest2.setUser(user);

        List<JoinRequest> joinRequests = new ArrayList<>();
        joinRequests.add(joinRequest1);
        joinRequests.add(joinRequest2);

        // Mock ponašanje
        when(joinRequestRepository.findAllByUserId(1)).thenReturn(joinRequests);

        // Test - findAllByUserId
        List<JoinRequest> retrievedRequests = joinRequestRepository.findAllByUserId(1);
        assertNotNull(retrievedRequests);
        assertEquals(2, retrievedRequests.size());

        // Verifikacija
        verify(joinRequestRepository, times(1)).findAllByUserId(1);
    }

    @Test
    void testDeleteJoinRequest() {
        // Mock JoinRequest
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setId(1);

        // Test - delete
        doNothing().when(joinRequestRepository).delete(joinRequest);

        joinRequestRepository.delete(joinRequest);

        // Verifikacija
        verify(joinRequestRepository, times(1)).delete(joinRequest);
    }

    @Test
    void testFindById() {
        // Mock JoinRequest
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setId(1);

        // Mock ponašanje
        when(joinRequestRepository.findById(1)).thenReturn(Optional.of(joinRequest));

        // Test - findById
        Optional<JoinRequest> retrievedRequest = joinRequestRepository.findById(1);
        assertTrue(retrievedRequest.isPresent());
        assertEquals(1, retrievedRequest.get().getId());

        // Verifikacija
        verify(joinRequestRepository, times(1)).findById(1);
    }
}
