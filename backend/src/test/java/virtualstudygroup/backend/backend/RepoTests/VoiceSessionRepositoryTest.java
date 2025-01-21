package virtualstudygroup.backend.backend.RepoTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.models.VirtualRoom;
import virtualstudygroup.backend.backend.models.VoiceSession;
import virtualstudygroup.backend.backend.repo.UserRepository;
import virtualstudygroup.backend.backend.repo.VirtualRoomRepository;
import virtualstudygroup.backend.backend.repo.VoiceSessionRepository;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VoiceSessionRepositoryTest {

    @Autowired
    private VoiceSessionRepository voiceSessionRepository;

    @Autowired
    private VirtualRoomRepository virtualRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindById() {
        // Kreiraj i spremi VirtualRoom
        VirtualRoom virtualRoom = new VirtualRoom();
        virtualRoom.setName("Test Room");
        virtualRoom = virtualRoomRepository.save(virtualRoom);

        // Kreiraj i spremi User
        User user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setPasswordHash("hashedpassword123");
        user = userRepository.save(user);

        // Kreiraj i spremi VoiceSession
        VoiceSession voiceSession = new VoiceSession();
        voiceSession.setVirtualRoom(virtualRoom);
        voiceSession.setCreatedBy(user);
        voiceSession.setStatus("ACTIVE");
        voiceSession.setCreatedAt(Instant.now());
        voiceSession = voiceSessionRepository.save(voiceSession);

        // Dohvati VoiceSession prema ID-ju
        Optional<VoiceSession> retrieved = voiceSessionRepository.findById(voiceSession.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("ACTIVE", retrieved.get().getStatus());
        assertEquals("Test Room", retrieved.get().getVirtualRoom().getName());
        assertEquals("Test User", retrieved.get().getCreatedBy().getName());
    }

    @Test
    void testDelete() {
        // Kreiraj potrebne entitete
        VirtualRoom virtualRoom = new VirtualRoom();
        virtualRoom.setName("Room to Delete");
        virtualRoom = virtualRoomRepository.save(virtualRoom);

        User user = new User();
        user.setName("Delete Test User");
        user.setEmail("deletetestuser@example.com");
        user.setPasswordHash("deletepassword123");
        user = userRepository.save(user);

        // Kreiraj i spremi VoiceSession
        VoiceSession voiceSession = new VoiceSession();
        voiceSession.setVirtualRoom(virtualRoom);
        voiceSession.setCreatedBy(user);
        voiceSession.setStatus("ENDED");
        voiceSession.setCreatedAt(Instant.now());
        voiceSession = voiceSessionRepository.save(voiceSession);

        // Obriši VoiceSession
        voiceSessionRepository.deleteById(voiceSession.getId());

        // Provjeri da je obrisan
        Optional<VoiceSession> retrieved = voiceSessionRepository.findById(voiceSession.getId());
        assertFalse(retrieved.isPresent());
    }
}
