package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.repo.StudyGroupRepository;

import java.util.Optional;

@Service
public class StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    private StudyGroupService (StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    };

    public Optional<StudyGroup> findById(Integer id) {
        return studyGroupRepository.findById(id);
    }
}
