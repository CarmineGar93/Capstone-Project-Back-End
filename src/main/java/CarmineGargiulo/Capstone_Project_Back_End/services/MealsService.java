package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.dto.ReferenceRecipeDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Meal;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Recipe;
import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.NotFoundException;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.MealsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealsService {
    @Autowired
    private MealsRepository mealsRepository;

    @Autowired
    private RecipesService recipesService;

    public List<Meal> saveManyMeals(List<Meal> mealList) {
        return mealsRepository.saveAll(mealList);
    }


    public Meal getMealById(long mealId) {
        return mealsRepository.findById(mealId).orElseThrow(() -> new NotFoundException("Meal with id " + mealId + " " +
                "not found"));
    }

    public void addRecipeToMeal(long mealId, ReferenceRecipeDTO body, User logged) {
        Meal meal = getMealById(mealId);
        if (meal.getDailyPlan().getWeeklyPlan().getUser().getUserId() != logged.getUserId())
            throw new AuthorizationDeniedException("You don't have access to modify this meal");
        LocalDate mealDate = meal.getDailyPlan().getWeeklyPlan().getStartDate().plusDays(meal.getDailyPlan().getDay());
        if (mealDate.isBefore(LocalDate.now()))
            throw new BadRequestException("You cannot add a recipe to a past day");
        Recipe recipeToAdd = recipesService.getRecipeByReference(body.reference());
        if (meal.getRecipe() != null) throw new BadRequestException("Meal has already a recipe");
        meal.addRecipe(recipeToAdd);
        mealsRepository.save(meal);
    }

    public void removeRecipeFromMeal(long mealId, User logged) {
        Meal meal = getMealById(mealId);
        if (meal.getDailyPlan().getWeeklyPlan().getUser().getUserId() != logged.getUserId())
            throw new AuthorizationDeniedException("You don't have access to modify this meal");
        LocalDate mealDate = meal.getDailyPlan().getWeeklyPlan().getStartDate().plusDays(meal.getDailyPlan().getDay());
        if (mealDate.isBefore(LocalDate.now()))
            throw new BadRequestException("You cannot remove a recipe to a past day");
        if (meal.getRecipe() == null) throw new BadRequestException("Meal has no recipe associated");
        meal.removeRecipe();
        mealsRepository.save(meal);
    }
}
