package virtualstudygroup.backend.backend.validation;

import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.repo.ValidationRule;

public class DescriptionMaxLengthRule implements ValidationRule<StudyGroup> {
    @Override
    public boolean validate(StudyGroup studyGroup) {
        return studyGroup.getDescription() == null || studyGroup.getDescription().length() <= 255;
    }

    @Override
    public String getErrorMessage() {
        return "Description cannot exceed 255 characters!";
    }
}