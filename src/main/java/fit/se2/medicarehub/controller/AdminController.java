package fit.se2.medicarehub.controller;

import fit.se2.medicarehub.model.Doctor;
import fit.se2.medicarehub.model.Patient;
import fit.se2.medicarehub.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/statistics")
    public String adminStatistics(Model model) {
        model.addAttribute("appointmentStats", adminService.appointmentStats());
        return "admin/statistics";
    }

    @GetMapping("/doctors")
    public String listDoctors(Model model) {
        List<Doctor> doctors = adminService.getAllDoctors();
        model.addAttribute("doctors", doctors);
        return "admin/doctors";
    }

    @GetMapping("/doctors/edit/{id}")
    public String showEditDoctorForm(@PathVariable("id") Long id, Model model) {
        Doctor doctor = adminService.getDoctorById(id);
        model.addAttribute("doctor", doctor);
        return "admin/doctor-form";
    }

    @PostMapping("/doctors/update")
    public String updateDoctor(@ModelAttribute("doctor") Doctor doctor) {
        adminService.updateDoctor(doctor);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id) {
        adminService.deleteDoctor(id);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/doctors/enable/{doctorId}")
    public String enableDoctor(@PathVariable("doctorId") Long doctorId) {
        adminService.updateDoctorEnabledStatus(doctorId, true);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/doctors/disable/{doctorId}")
    public String disableDoctor(@PathVariable("doctorId") Long doctorId) {
        adminService.updateDoctorEnabledStatus(doctorId, false);
        return "redirect:/admin/doctors";
    }

    @GetMapping("/patients")
    public String listPatients(Model model) {
        List<Patient> patients = adminService.getAllPatients();
        model.addAttribute("patients", patients);
        return "admin/patients";
    }

    @GetMapping("/patients/new")
    public String showAddPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "admin/patient-form";
    }

    @PostMapping("/patients")
    public String addPatient(@ModelAttribute("patient") Patient patient) {
        adminService.createPatient(patient);
        return "redirect:/admin/patients";
    }

    @GetMapping("/patients/edit/{id}")
    public String showEditPatientForm(@PathVariable("id") Long id, Model model) {
        Patient patient = adminService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "admin/patient-form";
    }

    @PostMapping("/patients/update")
    public String updatePatient(@ModelAttribute("patient") Patient patient) {
        adminService.updatePatient(patient);
        return "redirect:/admin/patients";
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id) {
        adminService.deletePatient(id);
        return "redirect:/admin/patients";
    }

    @GetMapping("/patients/enable/{patientId}")
    public String enablePatient(@PathVariable("patientId") Long patientId) {
        adminService.updatePatientEnabledStatus(patientId, true);
        return "redirect:/admin/patients";
    }

    @GetMapping("/patients/disable/{patientId}")
    public String disablePatient(@PathVariable("patientId") Long patientId) {
        adminService.updatePatientEnabledStatus(patientId, false);
        return "redirect:/admin/patients";
    }

    //endpoint working schedule
}
