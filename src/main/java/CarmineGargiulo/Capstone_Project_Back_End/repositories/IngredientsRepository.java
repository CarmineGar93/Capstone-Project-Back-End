package CarmineGargiulo.Capstone_Project_Back_End.repositories;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Ingredient;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientsRepository extends JpaRepository<Ingredient, Long> {
    Optional<Ingredient> findByProductAndQtyAndUnit(Product product, double qty, String unit);
}
