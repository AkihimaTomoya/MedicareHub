package fit.se2.medicarehub.service;

import fit.se2.medicarehub.model.AppointmentStats;
import fit.se2.medicarehub.model.Doctor;
import fit.se2.medicarehub.model.Patient;
import fit.se2.medicarehub.model.User;
import fit.se2.medicarehub.repository.AppointmentStatsRepository;
import fit.se2.medicarehub.repository.DoctorRepository;
import fit.se2.medicarehub.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentStatsRepository appointmentStatsRepository;

    public AdminService(DoctorRepository doctorRepository, PatientRepository patientRepository, AppointmentStatsRepository appointmentStatsRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentStatsRepository = appointmentStatsRepository;
    }
    public List<AppointmentStats> appointmentStats() {
        return appointmentStatsRepository.findAll();
    }
    // Quản lý Doctor
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

    public void updateDoctorEnabledStatus(Long doctorId, boolean enabled) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        User user = doctor.getUser();
        user.setEnabled(enabled);
        doctorRepository.save(doctor);
    }

    // Quản lý Patient
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public void updatePatientEnabledStatus(Long patientId, boolean enabled) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        User user = patient.getUser();
        user.setEnabled(enabled);
        patientRepository.save(patient);
    }
}
