package fit.se2.medicarehub.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Permission")
@Data
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permissionID;

    private String permissionName;

}
