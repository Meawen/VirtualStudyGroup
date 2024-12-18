package virtualstudygroup.backend.backend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.service.StudyGroupService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/study-groups")
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @Autowired
    public StudyGroupController(StudyGroupService studyGroupService) {
        this.studyGroupService = studyGroupService;
    }

    // Provjeri može li korisnik pristupiti grupi
    @GetMapping("/{groupId}/access/{userId}")
    public ResponseEntity<StudyGroup> checkAccessToGroup(@PathVariable Integer groupId, @PathVariable Integer userId) {
        return studyGroupService.getGroupIfUserHasAccess(groupId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(403).build());
    }


    // Get all Public StudyGroups
    @GetMapping("/public")
    public ResponseEntity<List<StudyGroup>> getAllPublicStudyGroups() {
        List<StudyGroup> studyGroups = studyGroupService.findAllPublic();
        return ResponseEntity.ok(studyGroups);
    }

    // Get Private StudyGroups for a specific User
    @GetMapping("/private/{userId}")
    public ResponseEntity<List<StudyGroup>> getPrivateGroupsForUser(@PathVariable Integer userId) {
        List<StudyGroup> studyGroups = studyGroupService.findPrivateGroupsByUser(userId);
        return ResponseEntity.ok(studyGroups);
    }

    // Create a new StudyGroup
    @PostMapping
    public ResponseEntity<StudyGroup> createStudyGroup(@Valid @RequestBody StudyGroup studyGroup) {
        StudyGroup createdStudyGroup = studyGroupService.create(studyGroup);
        return ResponseEntity.ok(createdStudyGroup);
    }
}
