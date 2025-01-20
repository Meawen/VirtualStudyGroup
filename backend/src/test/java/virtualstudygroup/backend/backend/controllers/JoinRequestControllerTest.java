package virtualstudygroup.backend.backend.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import virtualstudygroup.backend.backend.controllers.JoinRequestController;
import virtualstudygroup.backend.backend.models.JoinRequest;
import virtualstudygroup.backend.backend.service.JoinRequestService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JoinRequestControllerTest {

    @InjectMocks
    private JoinRequestController joinRequestController;

    @Mock
    private JoinRequestService joinRequestService;

    @Test
    public void testCreateJoinRequest() {
        JoinRequest request = new JoinRequest();
        request.setId(1);

        when(joinRequestService.create(any(JoinRequest.class))).thenReturn(request);

        ResponseEntity<JoinRequest> response = joinRequestController.createJoinRequest(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    public void testGetJoinRequestsByGroupId() {
        JoinRequest request1 = new JoinRequest();
        JoinRequest request2 = new JoinRequest();

        List<JoinRequest> requests = List.of(request1, request2);

        when(joinRequestService.findAllByGroupId(1)).thenReturn(requests);

        ResponseEntity<List<JoinRequest>> response = joinRequestController.getJoinRequestsByGroupId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testAcceptJoinRequest() {
        JoinRequest updatedRequest = new JoinRequest();
        updatedRequest.setId(1);
        updatedRequest.setStatus("ACCEPTED");

        when(joinRequestService.acceptRequest(1)).thenReturn(updatedRequest);

        ResponseEntity<JoinRequest> response = joinRequestController.acceptJoinRequest(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ACCEPTED", response.getBody().getStatus());
    }

    @Test
    public void testRejectJoinRequest() {
        doNothing().when(joinRequestService).rejectRequest(1);

        ResponseEntity<Void> response = joinRequestController.rejectJoinRequest(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(joinRequestService, times(1)).rejectRequest(1);
    }
}
