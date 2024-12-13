package virtualstudygroup.backend.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import virtualstudygroup.backend.backend.models.Membership;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.repo.MembershipRepository;
import virtualstudygroup.backend.backend.repo.StudyGroupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudyGroupService {
    private final StudyGroupRepository studyGroupRepository;
    private final MembershipRepository membershipRepository;



    @Autowired
    public StudyGroupService(StudyGroupRepository studyGroupRepository, MembershipRepository membershipRepository) {
        this.studyGroupRepository = studyGroupRepository;
        this.membershipRepository = membershipRepository;
    }


    public StudyGroup create(StudyGroup studyGroup) {
        return studyGroupRepository.save(studyGroup);
    }


    public Optional<StudyGroup> findById(Integer id) {
        return studyGroupRepository.findById(id);
    }

    public List<StudyGroup> findAll() {
        return studyGroupRepository.findAll();
    }

    public List<StudyGroup> findAllPublic() {
        return studyGroupRepository.findAllByVisibility("PUBLIC");
    }

    public List<StudyGroup> findPrivateGroupsByUser(Integer userId) {
        return studyGroupRepository.findAllByVisibilityAndCreatedById("PRIVATE", userId);
    }

    public StudyGroup update(Integer id, StudyGroup updatedStudyGroup) {
        return studyGroupRepository.findById(id).map(studyGroup -> {
            studyGroup.setName(updatedStudyGroup.getName());
            studyGroup.setDescription(updatedStudyGroup.getDescription());
            studyGroup.setVisibility(updatedStudyGroup.getVisibility());
            studyGroup.setUpdatedAt(updatedStudyGroup.getUpdatedAt());
            return studyGroupRepository.save(studyGroup);
        }).orElseThrow(() -> new IllegalArgumentException("StudyGroup with ID " + id + " not found"));
    }

    // Delete a StudyGroup by ID
    public void deleteById(Integer id) {
        if (studyGroupRepository.existsById(id)) {
            studyGroupRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("StudyGroup with ID " + id + " not found");
        }
    }

    public Optional<StudyGroup> getGroupIfUserHasAccess(Integer groupId, Integer userId) {
        Optional<StudyGroup> group = studyGroupRepository.findById(groupId);
        if (group.isPresent()) {
            StudyGroup studyGroup = group.get();

            // Ako je grupa javna, svatko može pristupiti
            if ("PUBLIC".equalsIgnoreCase(studyGroup.getVisibility())) {
                return Optional.of(studyGroup);
            }

            // Ako je korisnik kreator grupe, ima pristup
            if (studyGroup.getCreatedBy().getId().equals(userId)) {
                return Optional.of(studyGroup);
            }

            // Provjeri je li korisnik član grupe
            Optional<Membership> membership = membershipRepository.findByUserIdAndGroupId(userId, groupId);
            if (membership.isPresent()) {
                return Optional.of(studyGroup);
            }
        }
        return Optional.empty();
    }


}
