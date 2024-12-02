package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Ingredient;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Product;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.IngredientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientsService {
    @Autowired
    private IngredientsRepository ingredientsRepository;

    public Ingredient getIngredientByProductAndQty(Product product, double qty, String unit) {
        return ingredientsRepository.findByProductAndQtyAndUnit(product, qty, unit).orElseGet(() -> saveIngredient(product, qty, unit));
    }

    public Ingredient saveIngredient(Product product, double qty, String unit) {
        return ingredientsRepository.save(new Ingredient(qty, unit, product));
    }
}
