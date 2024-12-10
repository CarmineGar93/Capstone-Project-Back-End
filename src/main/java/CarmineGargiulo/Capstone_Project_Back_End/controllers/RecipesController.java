package CarmineGargiulo.Capstone_Project_Back_End.controllers;

import CarmineGargiulo.Capstone_Project_Back_End.dto.RecipeResponseDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Recipe;
import CarmineGargiulo.Capstone_Project_Back_End.services.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipesController {
    @Autowired
    private RecipesService recipesService;

    @GetMapping("/search")
    public List<RecipeResponseDTO> getRecipesByQuery(@RequestParam(name = "query") String query) {
        return recipesService.getRecipesByQuery(query);
    }

    @GetMapping
    public Page<Recipe> getRecipes(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "name") String sortBy) {
        return recipesService.getRecipes(page, sortBy);
    }

    @GetMapping("/{recipeReference}")
    public HashMap getRecipeInfo(@PathVariable long recipeReference) {
        return recipesService.getRecipeInfo(recipeReference);
    }

    @GetMapping("/random")
    public HashMap getRandomRecipes() {
        return recipesService.getRandomRecipes();
    }

    @PatchMapping("/{recipeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void resetIngredients(@PathVariable long recipeId) {
        recipesService.resetIngredients(recipeId);
    }
}
