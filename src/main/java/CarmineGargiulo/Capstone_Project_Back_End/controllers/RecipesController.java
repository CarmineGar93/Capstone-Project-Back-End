package CarmineGargiulo.Capstone_Project_Back_End.controllers;

import CarmineGargiulo.Capstone_Project_Back_End.dto.RecipeResponseDTO;
import CarmineGargiulo.Capstone_Project_Back_End.services.RecipesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipesController {
    @Autowired
    private RecipesService recipesService;

    @GetMapping
    public List<RecipeResponseDTO> getRecipes(@RequestParam(name = "query") String query) {
        return recipesService.getRecipesByQuery(query);
    }

    @GetMapping("/{recipeReference}")
    public HashMap getRecipeInfo(@PathVariable long recipeReference) {
        return recipesService.getRecipeInfo(recipeReference);
    }

    @GetMapping("/random")
    public HashMap getRandomRecipes() {
        return recipesService.getRandomRecipes();
    }
}
