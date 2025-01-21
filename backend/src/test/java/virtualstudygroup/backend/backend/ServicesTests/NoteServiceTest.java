package virtualstudygroup.backend.backend.ServicesTests;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.Note;
import virtualstudygroup.backend.backend.repo.NoteRepository;
import virtualstudygroup.backend.backend.service.NoteService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    public NoteServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNote() {
        // Arrange
        Note note = new Note();
        note.setContent("This is a test note");
        when(noteRepository.save(note)).thenReturn(note);

        // Act
        Note createdNote = noteService.create(note);

        // Assert
        assertThat(createdNote).isNotNull();
        assertThat(createdNote.getContent()).isEqualTo("This is a test note");
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    public void testFindNoteById() {
        // Arrange
        Note note = new Note();
        note.setId(1);
        note.setContent("Sample Note");
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));

        // Act
        Optional<Note> foundNote = noteService.findById(1);

        // Assert
        assertThat(foundNote).isPresent();
        assertThat(foundNote.get().getContent()).isEqualTo("Sample Note");
        verify(noteRepository, times(1)).findById(1);
    }

    @Test
    public void testFindAllNotes() {
        // Arrange
        Note note1 = new Note();
        note1.setContent("Note 1");
        Note note2 = new Note();
        note2.setContent("Note 2");
        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));

        // Act
        List<Note> notes = noteService.findAll();

        // Assert
        assertThat(notes).hasSize(2);
        assertThat(notes.get(0).getContent()).isEqualTo("Note 1");
        assertThat(notes.get(1).getContent()).isEqualTo("Note 2");
        verify(noteRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllNotesByGroupId() {
        // Arrange
        Note note1 = new Note();
        note1.setContent("Group Note 1");
        Note note2 = new Note();
        note2.setContent("Group Note 2");
        when(noteRepository.findAllByGroupId(1)).thenReturn(Arrays.asList(note1, note2));

        // Act
        List<Note> notes = noteService.findAllByGroupId(1);

        // Assert
        assertThat(notes).hasSize(2);
        verify(noteRepository, times(1)).findAllByGroupId(1);
    }

    @Test
    public void testFindAllNotesByAuthorId() {
        // Arrange
        Note note1 = new Note();
        note1.setContent("Author Note 1");
        Note note2 = new Note();
        note2.setContent("Author Note 2");
        when(noteRepository.findAllByAuthorId(1)).thenReturn(Arrays.asList(note1, note2));

        // Act
        List<Note> notes = noteService.findAllByAuthorId(1);

        // Assert
        assertThat(notes).hasSize(2);
        verify(noteRepository, times(1)).findAllByAuthorId(1);
    }

    @Test
    public void testUpdateNote() {
        // Arrange
        Note existingNote = new Note();
        existingNote.setId(1);
        existingNote.setContent("Old Content");

        Note updatedNote = new Note();
        updatedNote.setContent("Updated Content");

        when(noteRepository.findById(1)).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(existingNote)).thenReturn(existingNote);

        // Act
        Note result = noteService.update(1, updatedNote);

        // Assert
        assertThat(result.getContent()).isEqualTo("Updated Content");
        verify(noteRepository, times(1)).findById(1);
        verify(noteRepository, times(1)).save(existingNote);
    }

    @Test
    public void testUpdateNote_NotFound() {
        // Arrange
        Note updatedNote = new Note();
        updatedNote.setContent("Updated Content");
        when(noteRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> noteService.update(1, updatedNote));
        verify(noteRepository, times(1)).findById(1);
    }

    @Test
    public void testDeleteNoteById() {
        // Arrange
        when(noteRepository.existsById(1)).thenReturn(true);

        // Act
        noteService.deleteById(1);

        // Assert
        verify(noteRepository, times(1)).existsById(1);
        verify(noteRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteNoteById_NotFound() {
        // Arrange
        when(noteRepository.existsById(1)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> noteService.deleteById(1));
        verify(noteRepository, times(1)).existsById(1);
    }
}
