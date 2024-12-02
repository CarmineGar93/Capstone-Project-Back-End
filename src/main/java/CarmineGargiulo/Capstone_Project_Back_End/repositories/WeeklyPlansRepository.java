package CarmineGargiulo.Capstone_Project_Back_End.repositories;

import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.entities.WeeklyPlan;
import CarmineGargiulo.Capstone_Project_Back_End.enums.PlanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WeeklyPlansRepository extends JpaRepository<WeeklyPlan, UUID> {
    Page<WeeklyPlan> findByUser(User user, Pageable pageable);

    boolean existsByUserAndStatus(User user, PlanStatus status);
}
