package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByUser_Email(String email);
    Optional<Patient> findByPatientCode(String patientCode);
    @Query("SELECT p FROM Patient p WHERE p.user.fullName = :fullName AND p.user.phoneNumber = :phoneNumber AND p.dob = :dob AND p.user.gender = :gender")
    Optional<Patient> findByUserFullNameAndPhoneNumberAndDobAndGender(
            String fullName,
            String phoneNumber,
            Date dob,
            String gender
    );
}
