package fit.se2.medicarehub.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "RolePermission")
@Data
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "roleID", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "permissionID", nullable = false)
    private Permission permission;
}
