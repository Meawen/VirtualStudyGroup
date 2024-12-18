package virtualstudygroup.backend.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import virtualstudygroup.backend.backend.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}