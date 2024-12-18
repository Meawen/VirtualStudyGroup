package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.JoinRequest;
import virtualstudygroup.backend.backend.repo.JoinRequestRepository;

import java.util.List;

@Service
public class JoinRequestService {
    private final JoinRequestRepository joinRequestRepository;

    @Autowired
    public JoinRequestService(JoinRequestRepository joinRequestRepository) {
        this.joinRequestRepository = joinRequestRepository;
    }

    // Kreiranje zahtjeva za pridruživanje
    public JoinRequest create(JoinRequest joinRequest) {
        return joinRequestRepository.save(joinRequest);
    }

    // Dohvati zahtjeve za određenu grupu
    public List<JoinRequest> findAllByGroupId(Integer groupId) {
        return joinRequestRepository.findAllByGroupId(groupId);
    }

    // Dohvati zahtjeve korisnika
    public List<JoinRequest> findAllByUserId(Integer userId) {
        return joinRequestRepository.findAllByUserId(userId);
    }

    // Prihvati zahtjev
    public JoinRequest acceptRequest(Integer requestId) {
        return joinRequestRepository.findById(requestId).map(request -> {
            request.setStatus("ACCEPTED");
            return joinRequestRepository.save(request);
        }).orElseThrow(() -> new IllegalArgumentException("JoinRequest with ID " + requestId + " not found"));
    }

    // Odbij zahtjev
    public void rejectRequest(Integer requestId) {
        joinRequestRepository.findById(requestId).ifPresent(request -> {
            request.setStatus("REJECTED");
            joinRequestRepository.save(request);
        });
    }
}
