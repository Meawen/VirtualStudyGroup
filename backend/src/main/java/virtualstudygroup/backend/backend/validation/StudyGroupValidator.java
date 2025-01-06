package virtualstudygroup.backend.backend.validation;
import virtualstudygroup.backend.backend.models.StudyGroup;
import virtualstudygroup.backend.backend.repo.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public class StudyGroupValidator {
    private final List<ValidationRule<StudyGroup>> rules = new ArrayList<>();

    public void addRule(ValidationRule<StudyGroup> rule) {
        rules.add(rule);
    }

    public List<String> validate(StudyGroup studyGroup) {
        List<String> errors = new ArrayList<>();
        for (ValidationRule<StudyGroup> rule : rules) {
            if (!rule.validate(studyGroup)) {
                errors.add(rule.getErrorMessage());
            }
        }
        return errors;
    }
}
