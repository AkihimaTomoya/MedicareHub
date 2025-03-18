package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
