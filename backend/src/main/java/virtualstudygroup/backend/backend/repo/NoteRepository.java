package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.Note;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    List<Note> findAllByGroupId(Integer groupId);
    List<Note> findAllByAuthorId(Integer authorId);
}