package virtualstudygroup.backend.backend.repo;

public interface ValidationRule<T> {
    boolean validate(T entity);
    String getErrorMessage();
}

