package CarmineGargiulo.Capstone_Project_Back_End.repositories;

import CarmineGargiulo.Capstone_Project_Back_End.entities.WeeklyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeeklyPlansRepository extends JpaRepository<WeeklyPlan, UUID> {
}
