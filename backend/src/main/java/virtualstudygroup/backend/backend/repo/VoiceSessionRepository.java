package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.VoiceSession;

public interface VoiceSessionRepository extends JpaRepository<VoiceSession, Integer> {
}