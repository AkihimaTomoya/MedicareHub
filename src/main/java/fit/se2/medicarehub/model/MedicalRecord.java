package fit.se2.medicarehub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "medical_record") // kiểu giống lịch sử khám ý
@Data
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordID;

    @ManyToOne
    @JoinColumn(name = "patientID", nullable = false)
    private Patient patient;

    private String diagnosis;
    private String treatment;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
}
