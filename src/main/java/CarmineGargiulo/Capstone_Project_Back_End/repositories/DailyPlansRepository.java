package CarmineGargiulo.Capstone_Project_Back_End.repositories;

import CarmineGargiulo.Capstone_Project_Back_End.entities.DailyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPlansRepository extends JpaRepository<DailyPlan, Long> {
}
