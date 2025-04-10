package fit.se2.medicarehub.service;

import fit.se2.medicarehub.model.*;
import fit.se2.medicarehub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicationReminderRepository medicationReminderRepository;
    @Autowired
    private AppointmentReminderRepository appointmentReminderRepository;

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

    public Prescription updatePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Optional<Prescription> findPrescriptionById(Long prescriptionId) {
        return prescriptionRepository.findById(prescriptionId);
    }

    public List<MedicationReminder> dueReminders (Date now) {
        return medicationReminderRepository.findByReminderTimeBefore(now);
    }

    public MedicationReminder updateMedicationReminder(MedicationReminder reminder) {
        return medicationReminderRepository.save(reminder);
    }

    public AppointmentReminder updateAppointmentReminder(AppointmentReminder reminder) {
        return appointmentReminderRepository.save(reminder);
    }

    public List<AppointmentReminder> dueAppointmentReminders (Date now) {
        return appointmentReminderRepository.findByReminderTimeLessThanEqualAndReminderStatusTrue(now);
    }

    public Optional<AppointmentReminder> findAppointmentReminderByAppointmentId(Long appointmentId) {
        return appointmentReminderRepository.findByAppointmentId(appointmentId);
    }

    public Optional<MedicationReminder> findMedicationReminderByPrescriptionId(Long prescriptionId) {
        return medicationReminderRepository.findByPrescriptionId(prescriptionId);
    }
}