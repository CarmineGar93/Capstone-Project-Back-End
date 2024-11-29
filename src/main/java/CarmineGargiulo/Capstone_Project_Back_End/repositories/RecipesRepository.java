package CarmineGargiulo.Capstone_Project_Back_End.repositories;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipesRepository extends JpaRepository<Recipe, Long> {
}
