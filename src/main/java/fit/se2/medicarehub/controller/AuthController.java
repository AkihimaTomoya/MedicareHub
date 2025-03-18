package fit.se2.medicarehub.controller;

import ch.qos.logback.core.model.Model;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/auth/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String passwordConfirm, Model model) {
       if (!passwordEncoder.matches(password, passwordConfirm)) {
           throw new IllegalArgumentException("Passwords do not match");
       }
       if (!userDetailsManager.userExists(username)) {
           UserDetails user = User.builder()
                   .username(username)
                   .password(passwordEncoder.encode(password))
                   .roles("PATIENT")
                   .build();
           userDetailsManager.createUser(user);
       }
       return "redirect:/login";
    }

    @GetMapping("/auth/register")
    public String registerForm() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
