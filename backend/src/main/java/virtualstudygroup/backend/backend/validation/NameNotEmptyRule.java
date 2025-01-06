package virtualstudygroup.backend.backend.validation;

import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.repo.ValidationRule;

public class NameNotEmptyRule implements ValidationRule<StudyGroup> {

    @Override
    public boolean validate(StudyGroup studyGroup) {
        return studyGroup.getName() != null && !studyGroup.getName().trim().isEmpty();
    }

    @Override
    public String getErrorMessage() {
        return "Study Group name cannot be empty!";
    }
}
