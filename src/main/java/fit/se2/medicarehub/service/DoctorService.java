package fit.se2.medicarehub.service;

import fit.se2.medicarehub.model.Doctor;
import fit.se2.medicarehub.model.Patient;
import fit.se2.medicarehub.repository.AppointmentRepository;
import fit.se2.medicarehub.repository.DoctorRepository;
import fit.se2.medicarehub.repository.MedicationReminderRepository;
import fit.se2.medicarehub.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MedicationReminderRepository medicationReminderRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor getCurrentDoctor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        System.out.println("email: " + email);
        return doctorRepository.findByUser_Email(email);
    }

}
