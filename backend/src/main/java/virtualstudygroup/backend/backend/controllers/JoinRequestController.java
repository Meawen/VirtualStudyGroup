package virtualstudygroup.backend.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import virtualstudygroup.backend.backend.models.JoinRequest;
import virtualstudygroup.backend.backend.service.JoinRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/join-requests")
public class JoinRequestController {

    private final JoinRequestService joinRequestService;

    @Autowired
    public JoinRequestController(JoinRequestService joinRequestService) {
        this.joinRequestService = joinRequestService;
    }

    // Kreiraj zahtjev za pridruživanje
    @PostMapping
    public ResponseEntity<JoinRequest> createJoinRequest(@RequestBody JoinRequest joinRequest) {
        JoinRequest createdRequest = joinRequestService.create(joinRequest);
        return ResponseEntity.ok(createdRequest);
    }

    // Dohvati zahtjeve za određenu grupu
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<JoinRequest>> getJoinRequestsByGroupId(@PathVariable Integer groupId) {
        List<JoinRequest> requests = joinRequestService.findAllByGroupId(groupId);
        return ResponseEntity.ok(requests);
    }

    // Prihvati zahtjev za pridruživanje
    @PutMapping("/accept/{requestId}")
    public ResponseEntity<JoinRequest> acceptJoinRequest(@PathVariable Integer requestId) {
        JoinRequest updatedRequest = joinRequestService.acceptRequest(requestId);
        return ResponseEntity.ok(updatedRequest);
    }

    // Odbij zahtjev za pridruživanje
    @PutMapping("/reject/{requestId}")
    public ResponseEntity<Void> rejectJoinRequest(@PathVariable Integer requestId) {
        joinRequestService.rejectRequest(requestId);
        return ResponseEntity.noContent().build();
    }
}