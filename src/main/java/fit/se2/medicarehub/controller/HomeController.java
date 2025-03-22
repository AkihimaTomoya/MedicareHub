package fit.se2.medicarehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String route() {
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String home() {
        return "homepage";
    }
}
