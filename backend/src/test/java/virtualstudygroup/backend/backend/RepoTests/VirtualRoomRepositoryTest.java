package virtualstudygroup.backend.backend.RepoTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.models.VirtualRoom;
import virtualstudygroup.backend.backend.repo.StudyGroupRepository;
import virtualstudygroup.backend.backend.repo.VirtualRoomRepository;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class VirtualRoomRepositoryTest {

    @Autowired
    private VirtualRoomRepository virtualRoomRepository;

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Test
    void testSaveAndFindById() {
        // Kreiraj i spremi StudyGroup
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("Test Group");
        studyGroup = studyGroupRepository.save(studyGroup);

        // Kreiraj i spremi VirtualRoom
        VirtualRoom virtualRoom = new VirtualRoom();
        virtualRoom.setGroup(studyGroup);
        virtualRoom.setName("Test Room");
        virtualRoom.setSettings("{\"audio\":true,\"video\":false}");
        virtualRoom.setTimer(60);
        virtualRoom.setCreatedAt(Instant.now());
        virtualRoom = virtualRoomRepository.save(virtualRoom);

        // Dohvati VirtualRoom prema ID-ju
        Optional<VirtualRoom> retrieved = virtualRoomRepository.findById(virtualRoom.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("Test Room", retrieved.get().getName());
        assertEquals("{\"audio\":true,\"video\":false}", retrieved.get().getSettings());
        assertEquals(60, retrieved.get().getTimer());
        assertEquals("Test Group", retrieved.get().getGroup().getName());
    }

    @Test
    void testDelete() {
        // Kreiraj i spremi StudyGroup
        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setName("Group to Delete");
        studyGroup = studyGroupRepository.save(studyGroup);

        // Kreiraj i spremi VirtualRoom
        VirtualRoom virtualRoom = new VirtualRoom();
        virtualRoom.setGroup(studyGroup);
        virtualRoom.setName("Room to Delete");
        virtualRoom.setSettings("{\"audio\":false,\"video\":true}");
        virtualRoom.setTimer(30);
        virtualRoom.setCreatedAt(Instant.now());
        virtualRoom = virtualRoomRepository.save(virtualRoom);

        // Obriši VirtualRoom
        virtualRoomRepository.deleteById(virtualRoom.getId());

        // Provjeri da je obrisan
        Optional<VirtualRoom> retrieved = virtualRoomRepository.findById(virtualRoom.getId());
        assertFalse(retrieved.isPresent());
    }
}
