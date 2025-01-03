package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.Note;
import virtualstudygroup.backend.backend.repo.NoteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note create(Note note) {
        return noteRepository.save(note);
    }

    public Optional<Note> findById(Integer id) {
        return noteRepository.findById(id);
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public List<Note> findAllByGroupId(Integer groupId) {
        return noteRepository.findAllByGroupId(groupId);
    }

    public List<Note> findAllByAuthorId(Integer authorId) {
        return noteRepository.findAllByAuthorId(authorId);
    }

    public Note update(Integer id, Note updatedNote) {
        return noteRepository.findById(id).map(note -> {
            note.setContent(updatedNote.getContent());
            note.setUpdatedAt(updatedNote.getUpdatedAt());
            return noteRepository.save(note);
        }).orElseThrow(() -> new IllegalArgumentException("Note with ID " + id + " not found"));
    }

    public void deleteById(Integer id) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Note with ID " + id + " not found");
        }
    }
}
