package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByUser_Email(String email);
}
