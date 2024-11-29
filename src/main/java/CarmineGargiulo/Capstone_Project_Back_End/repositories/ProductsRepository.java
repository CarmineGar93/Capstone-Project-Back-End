package CarmineGargiulo.Capstone_Project_Back_End.repositories;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Product, Long> {
}
