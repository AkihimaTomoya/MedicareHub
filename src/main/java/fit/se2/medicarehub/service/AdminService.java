package fit.se2.medicarehub.service;

import fit.se2.medicarehub.model.*;
import fit.se2.medicarehub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentStatsRepository appointmentStatsRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private SpecialtyRepository specialtyRepository;

    public List<AppointmentStats> appointmentStats() {
        return appointmentStatsRepository.findAll();
    }
    // Quản lý Doctor
    public Page<Doctor> getAllDoctors(String fullName, String sortField, String sortDir, Pageable pageable) {
        return adminDao.filterAndSortDoctors(fullName, sortField, sortDir, pageable);
    }

    public Doctor getDoctorById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElse(null);
    }

    public Doctor createDoctor(Doctor doctor) {
        if (doctor.getUser() == null) {
            throw new IllegalArgumentException("Doctor must have an associated User");
        }

        Role doctorRole = roleRepository.findByRoleName("DOCTOR")
                .orElseThrow(() -> new RuntimeException("DOCTOR role not found"));

        if (doctor.getUser().getRoleID() == null ||
                !doctor.getUser().getRoleID().getRoleName().equalsIgnoreCase("DOCTOR")) {
            doctor.getUser().setRoleID(doctorRole);
        }
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
    public Page<Patient> getAllPatients(String fullName, String sortField, String sortDir, Pageable pageable) {
        return adminDao.filterAndSortPatients(fullName, sortField, sortDir, pageable);
    }

    public Patient getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElse(null);
    }

    public void updatePatientEnabledStatus(Long patientId, boolean enabled) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        User user = patient.getUser();
        user.setEnabled(enabled);
        patientRepository.save(patient);
    }

    public List<Specialty> getAllSpecialty() {
        return specialtyRepository.findAll();
    }

    public Specialty getSpecialtyById(Long id) {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        return specialty.orElse(null);
    }

    public Specialty createSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public Specialty updateSpecialty(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    public void updateSpecialtyEnabledStatus(Long specialtyId, boolean enabled) {
        Specialty specialty = specialtyRepository.findById(specialtyId)
                .orElseThrow(() -> new RuntimeException("Specialty not found"));
        specialty.setSpecialtyStatus(enabled);
        specialtyRepository.save(specialty);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesByDoctorId(Long doctorId) {
        return scheduleRepository.getSchedulesByDoctorDoctorID(doctorId);
    }
}
