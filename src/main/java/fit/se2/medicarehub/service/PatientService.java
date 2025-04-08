package fit.se2.medicarehub.service;

import fit.se2.medicarehub.model.Patient;
import fit.se2.medicarehub.model.User;
import fit.se2.medicarehub.repository.AppointmentRepository;
import fit.se2.medicarehub.repository.MedicationReminderRepository;
import fit.se2.medicarehub.repository.PatientRepository;
import fit.se2.medicarehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicationReminderRepository medicationReminderRepository;

    public Patient getCurrentPatient() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return patientRepository.findByUser_Email(email);
    }

    public void hideCurrentPatientById(Long patientId) {
        Patient patient = getCurrentPatient();
        if (patient != null) {
            patient.setDeleted(true);
            patientRepository.save(patient);
        }
    }
    public Patient createPatient(Patient patient) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByEmail(email);
        patient.setUser(user.get());

        String patientCode = "BN" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        patient.setPatientCode(patientCode);
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Optional<Patient> findByPatientCode(String patientCode) {
        Optional<Patient> patient = patientRepository.findByPatientCode(patientCode);
        patient.ifPresent(p -> p.setDeleted(false));
        return patient;
    }

    public Optional<Patient> findByFullNamePhoneDobGender(String fullName, String phoneNumber, Date dob, String gender) {
        return patientRepository.findByUserFullNameAndPhoneNumberAndDobAndGender(fullName, phoneNumber, dob, gender);
    }

}