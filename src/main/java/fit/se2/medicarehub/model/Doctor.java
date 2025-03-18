package fit.se2.medicarehub.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Doctor")
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorID;

    @OneToOne
    @JoinColumn(name = "userID", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "specialtyID", nullable = false)
    private Specialty specialty;

    private String licenseNumber;
    private String clinicAddress;

    //Validation data


}
