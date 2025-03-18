package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.MedicationReminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationReminderRepository extends JpaRepository<MedicationReminder, Long> {
}
