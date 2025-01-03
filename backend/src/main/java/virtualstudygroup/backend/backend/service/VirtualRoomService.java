package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.VirtualRoom;
import virtualstudygroup.backend.backend.repo.VirtualRoomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VirtualRoomService {
    private final VirtualRoomRepository virtualRoomRepository;

    @Autowired
    public VirtualRoomService(VirtualRoomRepository virtualRoomRepository) {
        this.virtualRoomRepository = virtualRoomRepository;
    }

    // Create a new VirtualRoom
    public VirtualRoom create(VirtualRoom virtualRoom) {
        return virtualRoomRepository.save(virtualRoom);
    }

    // Read a VirtualRoom by ID
    public Optional<VirtualRoom> findById(Integer id) {
        return virtualRoomRepository.findById(id);
    }

    public List<VirtualRoom> findAll() {
        return virtualRoomRepository.findAll();
    }

    public VirtualRoom update(Integer id, VirtualRoom updatedVirtualRoom) {
        return virtualRoomRepository.findById(id).map(virtualRoom -> {
            virtualRoom.setName(updatedVirtualRoom.getName());
            virtualRoom.setTimer(updatedVirtualRoom.getTimer());
            virtualRoom.setSettings(updatedVirtualRoom.getSettings());
            virtualRoom.setCreatedAt(updatedVirtualRoom.getCreatedAt());
            // Update other fields as necessary
            return virtualRoomRepository.save(virtualRoom);
        }).orElseThrow(() -> new IllegalArgumentException("VirtualRoom with ID " + id + " not found"));
    }

    // Delete a VirtualRoom by ID
    public void deleteById(Integer id) {
        if (virtualRoomRepository.existsById(id)) {
            virtualRoomRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("VirtualRoom with ID " + id + " not found");
        }
    }
}
