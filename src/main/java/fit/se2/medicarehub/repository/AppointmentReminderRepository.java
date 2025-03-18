package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.AppointmentReminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentReminderRepository extends JpaRepository<AppointmentReminder, Long> {
}
