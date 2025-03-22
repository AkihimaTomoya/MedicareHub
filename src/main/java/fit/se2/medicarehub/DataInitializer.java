package fit.se2.medicarehub;

import fit.se2.medicarehub.model.Admin;
import fit.se2.medicarehub.model.Role;
import fit.se2.medicarehub.model.User;
import fit.se2.medicarehub.repository.AdminRepository;
import fit.se2.medicarehub.repository.RoleRepository;
import fit.se2.medicarehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

@Configuration
public class DataInitializer {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initializeAdminAccount() {
        return args -> {
            String adminEmail = "admin@example.com";

            // Kiểm tra xem tài khoản admin theo email đã tồn tại chưa
            if (userRepository.findByEmail(adminEmail) == null) {
                // Kiểm tra xem role "ADMIN" đã tồn tại chưa
                Optional<Role> adminRoleOpt = roleRepository.findByRoleName("ROLE_ADMIN");
                Role adminRole;
                if (adminRoleOpt.isPresent()) {
                    adminRole = adminRoleOpt.get();
                } else {
                    // Nếu chưa có, tạo mới role ADMIN
                    adminRole = new Role();
                    adminRole.setRoleName("ROLE_ADMIN");
                    roleRepository.save(adminRole);
                }

                // Tạo tài khoản admin mới
                User admin = new User();
                // Nếu User vẫn yêu cầu username, có thể set bằng email
                admin.setUsername(adminEmail);
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEnabled(true);
                admin.setRoleID(adminRole);
                admin.setCreatedAt(new Date());

                User adminUser = userRepository.save(admin);

                // Tạo và lưu admin entity liên kết với user admin
                Admin adminEntity = new Admin();
                adminEntity.setUser(adminUser);
                adminRepository.save(adminEntity);
                System.out.println("Admin account created: " + adminEmail);
            } else {
                System.out.println("Admin account already exists: " + adminEmail);
            }
        };
    }

    @Bean
    public CommandLineRunner initializeDoctorAndPatientRoles(RoleRepository roleRepository) {
        return args -> {
            // Kiểm tra và tạo role DOCTOR nếu chưa tồn tại
            Optional<Role> doctorRoleOpt = roleRepository.findByRoleName("ROLE_DOCTOR");
            if (!doctorRoleOpt.isPresent()) {
                Role doctorRole = new Role();
                doctorRole.setRoleName("ROLE_DOCTOR");
                roleRepository.save(doctorRole);
                System.out.println("Doctor role created");
            } else {
                System.out.println("Doctor role already exists");
            }

            // Kiểm tra và tạo role PATIENT nếu chưa tồn tại
            Optional<Role> patientRoleOpt = roleRepository.findByRoleName("ROLE_PATIENT");
            if (!patientRoleOpt.isPresent()) {
                Role patientRole = new Role();
                patientRole.setRoleName("ROLE_PATIENT");
                roleRepository.save(patientRole);
                System.out.println("Patient role created");
            } else {
                System.out.println("Patient role already exists");
            }
        };
    }
}
