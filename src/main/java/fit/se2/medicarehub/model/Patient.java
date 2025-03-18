package fit.se2.medicarehub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Patient")
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientID;
    @OneToOne
    @JoinColumn(name = "userID", nullable = false, unique = true)
    private User user;

    @Temporal(TemporalType.DATE)
    private Date dob;

    private String address;

    //Validation data

}
