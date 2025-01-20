package virtualstudygroup.backend.backend.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import virtualstudygroup.backend.backend.controllers.NoteController;
import virtualstudygroup.backend.backend.models.Note;
import virtualstudygroup.backend.backend.service.NoteService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NoteControllerTest {

    @InjectMocks
    private NoteController noteController;

    @Mock
    private NoteService noteService;

    @Test
    public void testCreateNote() {
        Note note = new Note();
        note.setId(1);
        note.setContent("Sample content");

        when(noteService.create(any(Note.class))).thenReturn(note);

        ResponseEntity<Note> response = noteController.createNote(note);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sample content", response.getBody().getContent());
    }

    @Test
    public void testGetNoteById_Found() {
        Note note = new Note();
        note.setId(1);
        note.setContent("Test content");

        when(noteService.findById(1)).thenReturn(Optional.of(note));

        ResponseEntity<Note> response = noteController.getNoteById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test content", response.getBody().getContent());
    }

    @Test
    public void testGetNoteById_NotFound() {
        when(noteService.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<Note> response = noteController.getNoteById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllNotes() {
        List<Note> notes = List.of(new Note(), new Note());
        when(noteService.findAll()).thenReturn(notes);

        ResponseEntity<List<Note>> response = noteController.getAllNotes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetAllNotesByGroupId() {
        List<Note> notes = List.of(new Note(), new Note());
        when(noteService.findAllByGroupId(1)).thenReturn(notes);

        ResponseEntity<List<Note>> response = noteController.getAllNotesByGroupId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetAllNotesByAuthorId() {
        List<Note> notes = List.of(new Note(), new Note());
        when(noteService.findAllByAuthorId(1)).thenReturn(notes);

        ResponseEntity<List<Note>> response = noteController.getAllNotesByAuthorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testUpdateNote_Success() {
        Note updatedNote = new Note();
        updatedNote.setId(1);
        updatedNote.setContent("Updated content");

        when(noteService.update(eq(1), any(Note.class))).thenReturn(updatedNote);

        ResponseEntity<Note> response = noteController.updateNote(1, updatedNote);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated content", response.getBody().getContent());
    }

    @Test
    public void testUpdateNote_NotFound() {
        when(noteService.update(eq(1), any(Note.class))).thenThrow(new IllegalArgumentException());

        ResponseEntity<Note> response = noteController.updateNote(1, new Note());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testDeleteNoteById_Success() {
        doNothing().when(noteService).deleteById(1);

        ResponseEntity<Void> response = noteController.deleteNoteById(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(noteService, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteNoteById_NotFound() {
        doThrow(new IllegalArgumentException()).when(noteService).deleteById(1);

        ResponseEntity<Void> response = noteController.deleteNoteById(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
