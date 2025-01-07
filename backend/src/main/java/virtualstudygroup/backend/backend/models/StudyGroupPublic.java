package virtualstudygroup.backend.backend.models;

public class StudyGroupPublic extends StudyGroup {
    @Override
    public boolean userCanJoin(Integer userId) {
        return true;
    }
}
