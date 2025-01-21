package virtualstudygroup.backend.backend.RepoTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import virtualstudygroup.backend.backend.models.Note;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.NoteRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteRepositoryTest {

    @Mock
    private NoteRepository noteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveNote() {
        // Mock StudyGroup
        StudyGroup group = new StudyGroup();
        group.setId(1);
        group.setName("Test Group");

        // Mock User
        User author = new User();
        author.setId(1);
        author.setName("Test User");

        // Mock Note
        Note note = new Note();
        note.setId(1);
        note.setGroup(group);
        note.setAuthor(author);
        note.setContent("Test Content");
        note.setCreatedAt(Instant.now());
        note.setUpdatedAt(Instant.now());

        // Mock ponašanje
        when(noteRepository.save(note)).thenReturn(note);

        // Test - save
        Note savedNote = noteRepository.save(note);
        assertNotNull(savedNote);
        assertEquals("Test Content", savedNote.getContent());
        assertEquals(group, savedNote.getGroup());
        assertEquals(author, savedNote.getAuthor());

        // Verifikacija
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void testFindAllByGroupId() {
        // Mock StudyGroup
        StudyGroup group = new StudyGroup();
        group.setId(1);
        group.setName("Test Group");

        // Mock Notes
        Note note1 = new Note();
        note1.setId(1);
        note1.setGroup(group);
        note1.setContent("Note 1 Content");
        note1.setCreatedAt(Instant.now());

        Note note2 = new Note();
        note2.setId(2);
        note2.setGroup(group);
        note2.setContent("Note 2 Content");
        note2.setCreatedAt(Instant.now());

        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        notes.add(note2);

        // Mock ponašanje
        when(noteRepository.findAllByGroupId(group.getId())).thenReturn(notes);

        // Test - findAllByGroupId
        List<Note> retrievedNotes = noteRepository.findAllByGroupId(group.getId());
        assertNotNull(retrievedNotes);
        assertEquals(2, retrievedNotes.size());
        assertEquals("Note 1 Content", retrievedNotes.get(0).getContent());
        assertEquals("Note 2 Content", retrievedNotes.get(1).getContent());

        // Verifikacija
        verify(noteRepository, times(1)).findAllByGroupId(group.getId());
    }

    @Test
    void testFindAllByAuthorId() {
        // Mock User
        User author = new User();
        author.setId(1);
        author.setName("Test User");

        // Mock Notes
        Note note1 = new Note();
        note1.setId(1);
        note1.setAuthor(author);
        note1.setContent("Author Note 1");
        note1.setCreatedAt(Instant.now());

        Note note2 = new Note();
        note2.setId(2);
        note2.setAuthor(author);
        note2.setContent("Author Note 2");
        note2.setCreatedAt(Instant.now());

        List<Note> notes = new ArrayList<>();
        notes.add(note1);
        notes.add(note2);

        // Mock ponašanje
        when(noteRepository.findAllByAuthorId(author.getId())).thenReturn(notes);

        // Test - findAllByAuthorId
        List<Note> retrievedNotes = noteRepository.findAllByAuthorId(author.getId());
        assertNotNull(retrievedNotes);
        assertEquals(2, retrievedNotes.size());
        assertEquals("Author Note 1", retrievedNotes.get(0).getContent());
        assertEquals("Author Note 2", retrievedNotes.get(1).getContent());

        // Verifikacija
        verify(noteRepository, times(1)).findAllByAuthorId(author.getId());
    }

    @Test
    void testFindAllByGroupIdNoResults() {
        // Mock ponašanje
        when(noteRepository.findAllByGroupId(999)).thenReturn(new ArrayList<>());

        // Test - no results
        List<Note> retrievedNotes = noteRepository.findAllByGroupId(999);
        assertNotNull(retrievedNotes);
        assertTrue(retrievedNotes.isEmpty());

        // Verifikacija
        verify(noteRepository, times(1)).findAllByGroupId(999);
    }
}
