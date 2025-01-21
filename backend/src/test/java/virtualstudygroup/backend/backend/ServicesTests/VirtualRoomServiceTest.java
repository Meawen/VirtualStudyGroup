package virtualstudygroup.backend.backend.ServicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.VirtualRoom;
import virtualstudygroup.backend.backend.repo.VirtualRoomRepository;
import virtualstudygroup.backend.backend.service.VirtualRoomService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VirtualRoomServiceTest {

    @Mock
    private VirtualRoomRepository virtualRoomRepository;

    @InjectMocks
    private VirtualRoomService virtualRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateVirtualRoom() {
        // Arrange
        VirtualRoom virtualRoom = new VirtualRoom();
        virtualRoom.setName("Test Room");
        virtualRoom.setTimer(30);
        virtualRoom.setCreatedAt(Instant.now());

        when(virtualRoomRepository.save(virtualRoom)).thenReturn(virtualRoom);

        // Act
        VirtualRoom createdRoom = virtualRoomService.create(virtualRoom);

        // Assert
        assertThat(createdRoom).isNotNull();
        assertThat(createdRoom.getName()).isEqualTo("Test Room");
        verify(virtualRoomRepository, times(1)).save(virtualRoom);
    }

    @Test
    void testFindById() {
        // Arrange
        VirtualRoom virtualRoom = new VirtualRoom();
        virtualRoom.setId(1);
        virtualRoom.setName("Test Room");

        when(virtualRoomRepository.findById(1)).thenReturn(Optional.of(virtualRoom));

        // Act
        Optional<VirtualRoom> foundRoom = virtualRoomService.findById(1);

        // Assert
        assertThat(foundRoom).isPresent();
        assertThat(foundRoom.get().getName()).isEqualTo("Test Room");
        verify(virtualRoomRepository, times(1)).findById(1);
    }

    @Test
    void testFindAll() {
        // Arrange
        VirtualRoom room1 = new VirtualRoom();
        room1.setName("Room 1");

        VirtualRoom room2 = new VirtualRoom();
        room2.setName("Room 2");

        when(virtualRoomRepository.findAll()).thenReturn(List.of(room1, room2));

        // Act
        List<VirtualRoom> rooms = virtualRoomService.findAll();

        // Assert
        assertThat(rooms).hasSize(2);
        assertThat(rooms.get(0).getName()).isEqualTo("Room 1");
        assertThat(rooms.get(1).getName()).isEqualTo("Room 2");
        verify(virtualRoomRepository, times(1)).findAll();
    }

    @Test
    void testUpdateVirtualRoom() {
        // Arrange
        VirtualRoom existingRoom = new VirtualRoom();
        existingRoom.setId(1);
        existingRoom.setName("Old Room");

        VirtualRoom updatedRoom = new VirtualRoom();
        updatedRoom.setName("Updated Room");
        updatedRoom.setTimer(45);

        when(virtualRoomRepository.findById(1)).thenReturn(Optional.of(existingRoom));
        when(virtualRoomRepository.save(any(VirtualRoom.class))).thenReturn(updatedRoom);

        // Act
        VirtualRoom result = virtualRoomService.update(1, updatedRoom);

        // Assert
        assertThat(result.getName()).isEqualTo("Updated Room");
        assertThat(result.getTimer()).isEqualTo(45);
        verify(virtualRoomRepository, times(1)).findById(1);
        verify(virtualRoomRepository, times(1)).save(any(VirtualRoom.class));
    }

    @Test
    void testDeleteById() {
        // Arrange
        when(virtualRoomRepository.existsById(1)).thenReturn(true);

        // Act
        virtualRoomService.deleteById(1);

        // Assert
        verify(virtualRoomRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteByIdThrowsException() {
        // Arrange
        when(virtualRoomRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> virtualRoomService.deleteById(99));
        verify(virtualRoomRepository, times(0)).deleteById(99);
    }
}
