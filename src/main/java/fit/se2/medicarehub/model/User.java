package fit.se2.medicarehub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @ManyToOne
    @JoinColumn(name = "roleID", nullable = false)
    private Role roleID;

    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private String gender;
    private String identityNumber;
    private String image;
    private boolean enabled;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
