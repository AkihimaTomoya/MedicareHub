package fit.se2.medicarehub.controller;

import fit.se2.medicarehub.model.Patient;
import fit.se2.medicarehub.model.User;
import fit.se2.medicarehub.repository.AppointmentRepository;
import fit.se2.medicarehub.repository.PatientRepository;
import fit.se2.medicarehub.repository.UserRepository;
import fit.se2.medicarehub.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @GetMapping("/")
    public String route() {
        return "redirect:patient/home";
    }

    @GetMapping("/home")
    public String homepage(Model model) {
        Patient patient = patientService.getCurrentPatient();
        if (patient == null) {
            patient = new Patient();
            patient.setUser(new User());
        }
        model.addAttribute("patient", patient);
        return "patient/home";
    }

    @GetMapping("/booking")
    public String booking(Model model) {
        Patient patient = patientService.getCurrentPatient();
        model.addAttribute("patient", patient);
        return "patient/booking";
    }
    @GetMapping("/report")
    public String record(Model model) {
        Patient patient = patientService.getCurrentPatient();
        model.addAttribute("patient", patient);
        return "patient/report";
    }

    @GetMapping("/create-report")
    public String createRecord(Model model) {
        if (patientService.getCurrentPatient() != null) {
            return "redirect:/patient/report";
        }
        model.addAttribute("patient", new Patient());
        return "patient/create-report";
    }

    @GetMapping("/update-report")
    public String updateRecord(Model model) {
        Patient patient = patientService.getCurrentPatient();
        if (patient == null) {
            return "redirect:/patient/create-report";
        }
        model.addAttribute("patient", patient);
        return "patient/update-report";
    }

    @PostMapping("/save-report")
    public String saveRecord(@ModelAttribute("patient") Patient patientForm) {
        Patient existing = patientService.getCurrentPatient();
        if (existing != null) {
            // Cập nhật thông tin Patient
            existing.setDob(patientForm.getDob());
            existing.setAddress(patientForm.getAddress());
            existing.setEthnicity(patientForm.getEthnicity());
            // Cập nhật các trường trong User
            if(existing.getUser() == null) {
                existing.setUser(patientForm.getUser());
            } else {
                existing.getUser().setPhoneNumber(patientForm.getUser().getPhoneNumber());
                existing.getUser().setGender(patientForm.getUser().getGender());
                existing.getUser().setEmail(patientForm.getUser().getEmail());
                existing.getUser().setFullName(patientForm.getUser().getFullName());
                existing.getUser().setIdentityNumber(patientForm.getUser().getIdentityNumber());
            }
            patientService.updatePatient(existing);
        } else {
            patientService.createPatient(patientForm);
        }
        return "redirect:/patient/report";
    }

    @GetMapping("/delete-report")
    public String deleteRecord() {
        patientService.deleteCurrentPatientRecord();
        return "redirect:/patient/report";
    }



}
