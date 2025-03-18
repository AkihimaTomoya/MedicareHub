package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
