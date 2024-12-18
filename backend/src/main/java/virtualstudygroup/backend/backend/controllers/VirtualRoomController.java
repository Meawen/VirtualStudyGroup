package virtualstudygroup.backend.backend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import virtualstudygroup.backend.backend.models.VirtualRoom;
import virtualstudygroup.backend.backend.service.VirtualRoomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/virtual-rooms")
public class VirtualRoomController {

    private final VirtualRoomService virtualRoomService;

    @Autowired
    public VirtualRoomController(VirtualRoomService virtualRoomService) {
        this.virtualRoomService = virtualRoomService;
    }

    // Create a new VirtualRoom
    @PostMapping
    public ResponseEntity<VirtualRoom> createVirtualRoom(@Valid @RequestBody VirtualRoom virtualRoom) {
        VirtualRoom createdVirtualRoom = virtualRoomService.create(virtualRoom);
        return new ResponseEntity<>(createdVirtualRoom, HttpStatus.CREATED);
    }

    // Get a VirtualRoom by ID
    @GetMapping("/{id}")
    public ResponseEntity<VirtualRoom> getVirtualRoomById(@PathVariable Integer id) {
        Optional<VirtualRoom> virtualRoom = virtualRoomService.findById(id);
        return virtualRoom.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get all VirtualRooms
    @GetMapping
    public ResponseEntity<List<VirtualRoom>> getAllVirtualRooms() {
        List<VirtualRoom> virtualRooms = virtualRoomService.findAll();
        return ResponseEntity.ok(virtualRooms);
    }

    // Update a VirtualRoom
    @PutMapping("/{id}")
    public ResponseEntity<VirtualRoom> updateVirtualRoom(@PathVariable Integer id, @Valid @RequestBody VirtualRoom updatedVirtualRoom) {
        try {
            VirtualRoom updated = virtualRoomService.update(id, updatedVirtualRoom);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a VirtualRoom by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVirtualRoomById(@PathVariable Integer id) {
        try {
            virtualRoomService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
