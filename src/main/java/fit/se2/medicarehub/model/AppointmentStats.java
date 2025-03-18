package fit.se2.medicarehub.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "AppointmentStats")
@Data
public class AppointmentStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statID;

    @ManyToOne
    @JoinColumn(name = "doctorID", nullable = false)
    private Doctor doctor;

    private Integer totalAppointment;
    private Integer completed;
    private Integer canceled;
}
