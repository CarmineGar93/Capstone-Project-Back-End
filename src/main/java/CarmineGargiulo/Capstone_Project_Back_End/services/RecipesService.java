package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.entities.Ingredient;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Product;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Recipe;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.RecipesRepository;
import CarmineGargiulo.Capstone_Project_Back_End.tools.SpoonacularSender;
import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipesService {
    @Autowired
    private RecipesRepository recipesRepository;

    @Autowired
    private SpoonacularSender spoonacularSender;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private IngredientsService ingredientsService;

    public Recipe getRecipeByReference(long reference) {
        return recipesRepository.findByReference(reference).orElseGet(() -> saveRecipeByReference(reference));
    }

    public Recipe saveRecipeByReference(long reference) {
        JSONObject myObj = spoonacularSender.getRecipeInfo(reference);
        String recipeImg = myObj.getString("image");
        String recipeName = myObj.getString("title");
        JSONObject nutritionObj = myObj.getJSONObject("nutrition");
        JSONArray nutrientsArray = nutritionObj.getJSONArray("nutrients");
        double calories = nutrientsArray.getJSONObject(0).getDouble("amount");
        Recipe recipe = new Recipe(reference, recipeImg, recipeName, calories);
        JSONArray ingredients = myObj.getJSONArray("extendedIngredients");
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredients.toList().stream().forEach(o -> {
            long ingredientId = ((JSONObject) o).getLong("id");
            long ingredientAmount = ((JSONObject) o).getLong("amount");
            String unit = ((JSONObject) o).getString("unit");
            Product product = productsService.getProductByReference(ingredientId, (JSONObject) o);
            Ingredient ingredient = ingredientsService.getIngredientByProductAndQty(product, ingredientAmount, unit);
            ingredientList.add(ingredient);
        });
        recipe.setIngredientList(ingredientList);
        return recipesRepository.save(recipe);
    }


}
