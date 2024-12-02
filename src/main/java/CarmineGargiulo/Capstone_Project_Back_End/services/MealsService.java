package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Meal;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.MealsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealsService {
    @Autowired
    private MealsRepository mealsRepository;

    public List<Meal> saveManyMeals(List<Meal> mealList) {
        return mealsRepository.saveAll(mealList);
    }
}
