package CarmineGargiulo.Capstone_Project_Back_End.controllers;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Recipe;
import CarmineGargiulo.Capstone_Project_Back_End.services.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
public class RecipesController {
    @Autowired
    private RecipesService recipesService;

    @GetMapping("/{recipeReference}")
    public Recipe getRecipeInfo(@PathVariable long recipeReference) {
        return recipesService.getRecipeByReference(recipeReference);
    }
}
