package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.MedicationReminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MedicationReminderRepository extends JpaRepository<MedicationReminder, Long> {
    List<MedicationReminder> findByReminderTimeBefore(Date now);
}
