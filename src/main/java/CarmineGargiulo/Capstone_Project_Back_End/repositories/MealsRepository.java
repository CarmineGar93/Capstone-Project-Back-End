package CarmineGargiulo.Capstone_Project_Back_End.repositories;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealsRepository extends JpaRepository<Meal, Long> {
}
