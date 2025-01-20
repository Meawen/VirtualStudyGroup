package virtualstudygroup.backend.backend.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import virtualstudygroup.backend.backend.controllers.VirtualRoomController;
import virtualstudygroup.backend.backend.models.VirtualRoom;
import virtualstudygroup.backend.backend.service.VirtualRoomService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VirtualRoomControllerTest {

    @InjectMocks
    private VirtualRoomController virtualRoomController;

    @Mock
    private VirtualRoomService virtualRoomService;

    @Test
    public void testCreateVirtualRoom() {
        VirtualRoom room = new VirtualRoom();
        room.setId(1);
        room.setName("Study Room 101");

        when(virtualRoomService.create(any(VirtualRoom.class))).thenReturn(room);

        ResponseEntity<VirtualRoom> response = virtualRoomController.createVirtualRoom(room);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Study Room 101", response.getBody().getName());
    }

    @Test
    public void testGetVirtualRoomById_Found() {
        VirtualRoom room = new VirtualRoom();
        room.setId(1);
        room.setName("Physics Room");

        when(virtualRoomService.findById(1)).thenReturn(Optional.of(room));

        ResponseEntity<VirtualRoom> response = virtualRoomController.getVirtualRoomById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Physics Room", response.getBody().getName());
    }

    @Test
    public void testGetVirtualRoomById_NotFound() {
        when(virtualRoomService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<VirtualRoom> response = virtualRoomController.getVirtualRoomById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllVirtualRooms() {
        List<VirtualRoom> rooms = List.of(new VirtualRoom(), new VirtualRoom());
        when(virtualRoomService.findAll()).thenReturn(rooms);

        ResponseEntity<List<VirtualRoom>> response = virtualRoomController.getAllVirtualRooms();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testUpdateVirtualRoom_Success() {
        VirtualRoom updatedRoom = new VirtualRoom();
        updatedRoom.setId(1);
        updatedRoom.setName("Updated Room");

        when(virtualRoomService.update(eq(1), any(VirtualRoom.class))).thenReturn(updatedRoom);

        ResponseEntity<VirtualRoom> response = virtualRoomController.updateVirtualRoom(1, updatedRoom);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Room", response.getBody().getName());
    }

    @Test
    public void testUpdateVirtualRoom_NotFound() {
        when(virtualRoomService.update(eq(1), any(VirtualRoom.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<VirtualRoom> response = virtualRoomController.updateVirtualRoom(1, new VirtualRoom());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteVirtualRoomById_Success() {
        doNothing().when(virtualRoomService).deleteById(1);

        ResponseEntity<Void> response = virtualRoomController.deleteVirtualRoomById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(virtualRoomService, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteVirtualRoomById_NotFound() {
        doThrow(new IllegalArgumentException()).when(virtualRoomService).deleteById(1);

        ResponseEntity<Void> response = virtualRoomController.deleteVirtualRoomById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
