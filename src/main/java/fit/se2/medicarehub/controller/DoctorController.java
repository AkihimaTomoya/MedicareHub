package fit.se2.medicarehub.controller;

import fit.se2.medicarehub.model.Doctor;
import fit.se2.medicarehub.model.Patient;
import fit.se2.medicarehub.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @GetMapping("/home")
    public String homepage(Model model) {
        Doctor doctor = doctorService.getCurrentDoctor();
        model.addAttribute("doctor", doctor);
        return "doctor/home";
    }

}
