package fit.se2.medicarehub.controller;

import fit.se2.medicarehub.model.Doctor;
import fit.se2.medicarehub.model.Patient;
import fit.se2.medicarehub.model.Schedule;
import fit.se2.medicarehub.model.Specialty;
import fit.se2.medicarehub.repository.SpecialtyRepository;
import fit.se2.medicarehub.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private SpecialtyRepository specialtyRepository;

    //Stats
    @GetMapping("/dashboard")
    public String adminStatistics(Model model) {
        model.addAttribute("appointmentStats", adminService.appointmentStats());
        return "admin/statistics";
    }

    //Doctor
    @GetMapping("/doctors")
    public String listDoctors(
            @RequestParam(value = "fullName", defaultValue = "") String fullName,
            @RequestParam(value = "sortField", defaultValue = "fullName") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {
        final int pageSize = 5;
        Page<Doctor> doctors = adminService.getAllDoctors(
                fullName, sortField, sortDir, PageRequest.of(page, pageSize));
        model.addAttribute("fullName", fullName);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("page", page);
        model.addAttribute("pages", doctors.getTotalPages());
        model.addAttribute("doctors", doctors.getContent());
        return "admin/doctor-list";
    }

    @GetMapping("/doctors/detail/{id}")
    public String getDoctorById(@PathVariable("id") Long id, Model model) {
        Doctor doctor = adminService.getDoctorById(id);
        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            return "admin/doctor-detail";
        }
        return "404";
    }

    @GetMapping("/doctors/add")
    public String addDoctor(Model model) {
        Doctor doctor = new Doctor();
        model.addAttribute("doctor", doctor);
        model.addAttribute("specialty", specialtyRepository.findAll());
        return "redirect:/admin/doctor-add";
    }

    @GetMapping("/doctors/update/{id}")
    public String updateDoctor(@PathVariable("id") Long id, Model model) {
        Doctor doctor = adminService.getDoctorById(id);
        if (doctor == null) {
            return "404";
        }
        model.addAttribute("doctor", doctor);
        model.addAttribute("specialty", specialtyRepository.findAll());
        return "admin/doctor-update";
    }

    @PostMapping("/doctors/save")
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor) {
        if (doctor.getDoctorID() == null) {
            adminService.createDoctor(doctor);
        } else {
            adminService.updateDoctor(doctor);
        }
        return "redirect:/doctor/detail/" + doctor.getDoctorID();
    }

    @GetMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id) {
        adminService.deleteDoctor(id);
        return "redirect:/admin/doctors";
    }


    //Patient
    @GetMapping("/patients")
    public String listPatients(
            @RequestParam(value = "fullName", defaultValue = "") String fullName,
            @RequestParam(value = "sortField", defaultValue = "fullName") String sortField,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {
        final int pageSize = 5;
        Page<Patient> patients = adminService.getAllPatients(
                fullName, sortField, sortDir, PageRequest.of(page, pageSize));
        model.addAttribute("fullName", fullName);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("page", page);
        model.addAttribute("pages", patients.getTotalPages());
        model.addAttribute("doctors", patients.getContent());
        return "admin/patient-list";
    }

    @GetMapping("/patients/detail/{id}")
    public String getPatientById(@PathVariable("id") Long id, Model model) {
        Patient patient = adminService.getPatientById(id);
        if (patient != null) {
            model.addAttribute("patient", patient);
            return "admin/patient-detail";
        }
        return "404";
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


    //Lịch trực
    @GetMapping("/schedules")
    public String listSchedules(Model model) {
        List<Schedule> schedules = adminService.getAllSchedules();
        model.addAttribute("schedules", schedules);
        return "admin/schedules";
    }

    //Khoa (có tìm kiếm, trang, ..)
    @GetMapping("/specialty")
    public String listSpecialty(Model model) {
        List<Specialty> specialties = adminService.getAllSpecialty();
        model.addAttribute("specialties", specialties);
        return "admin/specialty-list";
    }

    @GetMapping("/specialty/detail/{id}")
    public String getSpecialtyById(@PathVariable("id") Long id, Model model) {
        Specialty specialty = adminService.getSpecialtyById(id);
        if (specialty != null) {
            model.addAttribute("specialty", specialty);
            return "admin/specialty-detail";
        }
        return "404";
    }

    @GetMapping("/specialty/add")
    public String addSpecialty(Model model) {
        Specialty specialty = new Specialty();
        model.addAttribute("specialty", specialty);
        return "admin/specialty-add";
    }


    // Xem lịch làm việc của một bác sĩ cụ thể
    @GetMapping("/schedules/doctor/{doctorId}")
    public String listDoctorSchedules(@PathVariable("doctorId") Long doctorID, Model model) {
        List<Schedule> schedules = adminService.getSchedulesByDoctorId(doctorID);
        model.addAttribute("schedules", schedules);
        return "admin/schedules";
    }

    //Lịch sử khám bệnh
}
