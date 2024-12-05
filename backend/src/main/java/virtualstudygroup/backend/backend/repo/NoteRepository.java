package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.Note;

public interface NoteRepository extends JpaRepository<Note, Integer> {
}