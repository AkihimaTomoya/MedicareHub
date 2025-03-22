package fit.se2.medicarehub.controller;

import fit.se2.medicarehub.model.Patient;
import fit.se2.medicarehub.model.Role;
import fit.se2.medicarehub.repository.PatientRepository;
import fit.se2.medicarehub.repository.RoleRepository;
import fit.se2.medicarehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import fit.se2.medicarehub.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PatientRepository patientRepository;

    // Endpoint đăng ký: tạo user với role PATIENT
    @PostMapping("/register")
    public String register(
            @RequestParam("email") String email,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            org.springframework.ui.Model model) {

        // Kiểm tra xác nhận mật khẩu
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Password confirmation does not match");
            return "homepage"; // Trả về trang đăng ký kèm lỗi
        }

        // Sử dụng email làm username
        if (userRepository.findByUsername(email) != null) {
            model.addAttribute("error", "User already exists");
            return "homepage";
        }

        Optional<Role> patientRoleOpt = roleRepository.findByRoleName("ROLE_PATIENT");
        if (!patientRoleOpt.isPresent()) {
            model.addAttribute("error", "Patient role not configured");
            return "homepage";
        }

        User newUser = new User();
        newUser.setUsername(email);
        newUser.setEmail(email);
        newUser.setFullName(lastName + " " + firstName);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEnabled(true);
        newUser.setRoleID(patientRoleOpt.get());
        newUser.setCreatedAt(new Date());

        User user = userRepository.save(newUser);

        Patient patient = new Patient();
        patient.setUser(user);
        patientRepository.save(patient);
        // Sau khi đăng ký thành công, redirect về trang đăng nhập
        return "redirect:/home";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.getSession(true)
                    .setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return "redirect:/admin/dashboard";
            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"))) {
                return "redirect:/doctor/home";
            } else {
                return "redirect:/patient/home";
            }
        } catch (AuthenticationException e) {
            return "redirect:/home?loginError=true";
        }
    }

    // Endpoint reset mật khẩu: cập nhật mật khẩu mới cho user
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        // Tìm user theo email
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found for email: " + email);
        }

        // Đặt lại mật khẩu về 123456 (đơn giản nhất)
        String newPasswordPlain = "123456";

        // Mã hoá và cập nhật
        user.setPassword(passwordEncoder.encode(newPasswordPlain));
        userRepository.save(user);

        // (Tuỳ chọn) Gửi email hoặc in ra console
        // In ra console cho nhanh:
        System.out.println("New password for " + email + " is: " + newPasswordPlain);

        // Hoặc nếu muốn gửi email:
        // sendResetEmail(email, newPasswordPlain);

        return ResponseEntity.ok("Password has been reset. Check console or email for the new password.");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // Hủy session hiện tại
        request.getSession().invalidate();
        // Xóa context của Spring Security
        SecurityContextHolder.clearContext();
        // Redirect về trang chủ (hoặc trang nào khác bạn mong muốn)
        return "redirect:/home?logout=true";
    }
}
