package virtualstudygroup.backend.backend.ServicesTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.JoinRequest;
import virtualstudygroup.backend.backend.repo.JoinRequestRepository;
import virtualstudygroup.backend.backend.service.JoinRequestService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class JoinRequestServiceTest {

    @Mock
    private JoinRequestRepository joinRequestRepository;

    @InjectMocks
    private JoinRequestService joinRequestService;

    public JoinRequestServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateJoinRequest() {
        // Arrange
        JoinRequest joinRequest = new JoinRequest();
        joinRequest.setStatus("PENDING");
        when(joinRequestRepository.save(joinRequest)).thenReturn(joinRequest);

        // Act
        JoinRequest createdJoinRequest = joinRequestService.create(joinRequest);

        // Assert
        assertThat(createdJoinRequest).isNotNull();
        assertThat(createdJoinRequest.getStatus()).isEqualTo("PENDING");
        verify(joinRequestRepository, times(1)).save(joinRequest);
    }

    @Test
    public void testFindAllByGroupId() {
        // Arrange
        JoinRequest request1 = new JoinRequest();
        JoinRequest request2 = new JoinRequest();
        when(joinRequestRepository.findAllByGroupId(1)).thenReturn(Arrays.asList(request1, request2));

        // Act
        List<JoinRequest> requests = joinRequestService.findAllByGroupId(1);

        // Assert
        assertThat(requests).hasSize(2);
        verify(joinRequestRepository, times(1)).findAllByGroupId(1);
    }

    @Test
    public void testFindAllByUserId() {
        // Arrange
        JoinRequest request1 = new JoinRequest();
        JoinRequest request2 = new JoinRequest();
        when(joinRequestRepository.findAllByUserId(1)).thenReturn(Arrays.asList(request1, request2));

        // Act
        List<JoinRequest> requests = joinRequestService.findAllByUserId(1);

        // Assert
        assertThat(requests).hasSize(2);
        verify(joinRequestRepository, times(1)).findAllByUserId(1);
    }

    @Test
    public void testAcceptRequest() {
        // Arrange
        JoinRequest request = new JoinRequest();
        request.setStatus("PENDING");
        when(joinRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(joinRequestRepository.save(request)).thenReturn(request);

        // Act
        JoinRequest acceptedRequest = joinRequestService.acceptRequest(1);

        // Assert
        assertThat(acceptedRequest.getStatus()).isEqualTo("ACCEPTED");
        verify(joinRequestRepository, times(1)).findById(1);
        verify(joinRequestRepository, times(1)).save(request);
    }

    @Test
    public void testAcceptRequest_NotFound() {
        // Arrange
        when(joinRequestRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> joinRequestService.acceptRequest(1));
        verify(joinRequestRepository, times(1)).findById(1);
    }

    @Test
    public void testRejectRequest() {
        // Arrange
        JoinRequest request = new JoinRequest();
        request.setStatus("PENDING");
        when(joinRequestRepository.findById(1)).thenReturn(Optional.of(request));

        // Act
        joinRequestService.rejectRequest(1);

        // Assert
        assertThat(request.getStatus()).isEqualTo("REJECTED");
        verify(joinRequestRepository, times(1)).findById(1);
        verify(joinRequestRepository, times(1)).save(request);
    }

    @Test
    public void testRejectRequest_NotFound() {
        // Arrange
        when(joinRequestRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        joinRequestService.rejectRequest(1);

        // Assert
        verify(joinRequestRepository, times(1)).findById(1);
        verify(joinRequestRepository, never()).save(any());
    }
}
