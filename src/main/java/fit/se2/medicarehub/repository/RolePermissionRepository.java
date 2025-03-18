package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}
