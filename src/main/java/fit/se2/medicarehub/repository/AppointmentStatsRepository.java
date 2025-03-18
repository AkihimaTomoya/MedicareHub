package fit.se2.medicarehub.repository;

import fit.se2.medicarehub.model.AppointmentStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentStatsRepository extends JpaRepository<AppointmentStats, Long> {
}
