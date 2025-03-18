package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
