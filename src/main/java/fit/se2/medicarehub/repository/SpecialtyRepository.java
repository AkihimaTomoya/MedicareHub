package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
}
