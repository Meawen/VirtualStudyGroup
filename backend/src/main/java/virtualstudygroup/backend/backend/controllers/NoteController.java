package virtualstudygroup.backend.backend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import virtualstudygroup.backend.backend.models.Note;
import virtualstudygroup.backend.backend.service.NoteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    // Create a new Note
    @PostMapping
    public ResponseEntity<Note> createNote(@Valid @RequestBody Note note) {
        Note createdNote = noteService.create(note);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    // Get a Note by ID
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Integer id) {
        Optional<Note> note = noteService.findById(id);
        return note.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get all Notes
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> notes = noteService.findAll();
        return ResponseEntity.ok(notes);
    }

    // Get all Notes by Group ID
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Note>> getAllNotesByGroupId(@PathVariable Integer groupId) {
        List<Note> notes = noteService.findAllByGroupId(groupId);
        return ResponseEntity.ok(notes);
    }

    // Get all Notes by Author ID
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Note>> getAllNotesByAuthorId(@PathVariable Integer authorId) {
        List<Note> notes = noteService.findAllByAuthorId(authorId);
        return ResponseEntity.ok(notes);
    }

    // Update a Note
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Integer id, @Valid @RequestBody Note updatedNote) {
        try {
            Note updated = noteService.update(id, updatedNote);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Delete a Note by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Integer id) {
        try {
            noteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
