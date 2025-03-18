package fit.se2.medicarehub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Table(name = "Appointment")
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentID;

    @ManyToOne
    @JoinColumn(name = "doctorID", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patientID", nullable = false)
    private Patient patient;

    @Temporal(TemporalType.TIMESTAMP)
    private Date appointmentDate;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
