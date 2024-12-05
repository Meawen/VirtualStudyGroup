package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.StudyGroup;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer> {
}