package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.StudyGroup;

import java.util.List;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer> {
    List<StudyGroup> findAllByVisibility(String visibility);
    List<StudyGroup> findAllByVisibilityAndCreatedById(String visibility, Integer userId);
}