package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.repo.StudyGroupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;

    @Autowired
    private StudyGroupService (StudyGroupRepository studyGroupRepository) {
        this.studyGroupRepository = studyGroupRepository;
    };

    public StudyGroup create(StudyGroup studyGroup) {
        return studyGroupRepository.save(studyGroup);
    }


    public Optional<StudyGroup> findById(Integer id) {
        return studyGroupRepository.findById(id);
    }


    public List<StudyGroup> findAll() {
        return studyGroupRepository.findAll();
    }


    public StudyGroup update(Integer id, StudyGroup updatedStudyGroup) {
        return studyGroupRepository.findById(id).map(studyGroup -> {
            studyGroup.setName(updatedStudyGroup.getName());
            studyGroup.setDescription(updatedStudyGroup.getDescription());
            studyGroup.setUpdatedAt(updatedStudyGroup.getUpdatedAt());
            // Add other fields to update as needed
            return studyGroupRepository.save(studyGroup);
        }).orElseThrow(() -> new IllegalArgumentException("StudyGroup with ID " + id + " not found"));
    }


    public void deleteById(Integer id) {
        if (studyGroupRepository.existsById(id)) {
            studyGroupRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("StudyGroup with ID " + id + " not found");
        }
    }


}
