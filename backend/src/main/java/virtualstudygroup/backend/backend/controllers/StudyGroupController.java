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


    @PostMapping
    public ResponseEntity<StudyGroup> createStudyGroup(@Valid @RequestBody StudyGroup studyGroup) {
        StudyGroup createdStudyGroup = studyGroupService.create(studyGroup);
        return new ResponseEntity<>(createdStudyGroup, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudyGroup> getStudyGroupById(@PathVariable Integer id) {
        Optional<StudyGroup> studyGroup = studyGroupService.findById(id);
        return studyGroup.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @GetMapping
    public ResponseEntity<List<StudyGroup>> getAllStudyGroups() {
        List<StudyGroup> studyGroups = studyGroupService.findAll();
        return ResponseEntity.ok(studyGroups);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StudyGroup> updateStudyGroup(@PathVariable Integer id, @Valid @RequestBody StudyGroup updatedStudyGroup) {
        try {
            StudyGroup updated = studyGroupService.update(id, updatedStudyGroup);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudyGroupById(@PathVariable Integer id) {
        try {
            studyGroupService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
