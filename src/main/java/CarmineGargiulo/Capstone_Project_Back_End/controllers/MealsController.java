package CarmineGargiulo.Capstone_Project_Back_End.controllers;

import CarmineGargiulo.Capstone_Project_Back_End.dto.UpdateMealsRecipeDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.services.MealsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/meals")
public class MealsController {
    @Autowired
    private MealsService mealsService;

    @PatchMapping("/{mealId}")
    public void addRecipe(@AuthenticationPrincipal User logged, @PathVariable long mealId,
                          @RequestBody @Validated UpdateMealsRecipeDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        mealsService.addRecipeToMeal(mealId, body, logged);
    }
}
