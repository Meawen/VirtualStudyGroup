package virtualstudygroup.backend.backend.models;

import virtualstudygroup.backend.backend.repo.MembershipRepository;

public class StudyGroupPrivate extends StudyGroup {
    private final MembershipRepository membershipRepository;

    public StudyGroupPrivate(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public boolean userCanJoin(Integer userId) {
        return membershipRepository.findByUserIdAndGroupId(userId, this.getId()).isPresent();
    }
}