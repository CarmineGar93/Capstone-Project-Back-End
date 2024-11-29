package CarmineGargiulo.Capstone_Project_Back_End.repositories;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {
}
