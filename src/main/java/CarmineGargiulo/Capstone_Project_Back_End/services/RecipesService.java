package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.dto.RecipeResponseDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Ingredient;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Product;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Recipe;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.NotFoundException;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.RecipesRepository;
import CarmineGargiulo.Capstone_Project_Back_End.tools.SpoonacularSender;
import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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

    public HashMap getRecipeInfo(long reference) {
        return spoonacularSender.getRecipeInfo(reference);
    }

    public HashMap getRandomRecipes() {
        return spoonacularSender.getRandomRecipes();
    }

    public Recipe saveRecipeByReference(long reference) {
        JSONObject myObj = spoonacularSender.getRecipeByReference(reference);
        String recipeImg = myObj.getString("image");
        String recipeName = myObj.getString("title");
        int readyIn;
        try {
            readyIn = myObj.getInt("readyInMinutes");
        } catch (Exception e) {
            readyIn = 0;
        }
        JSONObject nutritionObj = myObj.getJSONObject("nutrition");
        JSONArray nutrientsArray = nutritionObj.getJSONArray("nutrients");
        double calories = nutrientsArray.getJSONObject(0).getDouble("amount");
        Recipe recipe = new Recipe(reference, recipeImg, recipeName, calories, readyIn);
        JSONArray ingredients = myObj.getJSONArray("extendedIngredients");
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredients.toList().stream().forEach(o -> {
            long ingredientId = ((JSONObject) o).getLong("id");
            if (ingredientId != -1) {
                JSONObject metricUnit = ((JSONObject) o).getJSONObject("measures").getJSONObject("metric");
                double ingredientAmount = metricUnit.getDouble("amount");
                String unit = metricUnit.getString("unitLong").toLowerCase();
                String unitToUse;
                switch (unit) {
                    case "tbsps":
                    case "tablespoon":
                    case "tablespoons":
                    case "tbsp":
                        unitToUse = "teaspoons";
                        ingredientAmount *= 3;
                        break;
                    case "dash":
                        unitToUse = "teaspoons";
                        ingredientAmount /= 8;
                        break;
                    case "teaspoon":
                        unitToUse = "teaspoons";
                        break;
                    case "pinch":
                    case "pinches":
                        unitToUse = "teaspoons";
                        ingredientAmount /= 16;
                        break;
                    case "serving":
                    case "servings":
                        unitToUse = "to taste";
                        ingredientAmount = 0;
                        break;
                    case "small":
                    case "smalls":
                        unitToUse = "pieces";
                        ingredientAmount *= 0.5;
                        break;
                    case "large":
                    case "larges":
                        unitToUse = "pieces";
                        ingredientAmount *= 1.5;
                        break;
                    case "cloves":
                        unitToUse = "pieces";
                        ingredientAmount /= 10;
                        break;
                    case "":
                    case "mediums":
                    case "medium":
                    case "stalk":
                    case "stalks":
                        unitToUse = "pieces";
                        break;
                    default:
                        unitToUse = unit;
                }
                Product product = productsService.getProductByReference(ingredientId, (JSONObject) o);
                Ingredient ingredient = ingredientsService.getIngredientByProductAndQty(product, ingredientAmount,
                        unitToUse);
                ingredientList.add(ingredient);
            }
        });
        recipe.setIngredientList(ingredientList);
        return recipesRepository.save(recipe);
    }

    public List<RecipeResponseDTO> getRecipesByQuery(String query) {
        JSONObject response = spoonacularSender.getRecipesByQuery(query);
        JSONArray results = response.getJSONArray("results");
        if (results.isEmpty()) throw new NotFoundException("Recipe not founded");
        List<RecipeResponseDTO> recipes = new ArrayList<>();
        results.toList().stream().forEach(o -> {
            long reference = ((JSONObject) o).getLong("id");
            String title = ((JSONObject) o).getString("title");
            String image = ((JSONObject) o).getString("image");
            recipes.add(new RecipeResponseDTO(reference, title, image));
        });
        return recipes;
    }

    public void resetIngredients(long id) {
        Recipe founded = recipesRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found"));
        founded.setIngredientList(null);
        recipesRepository.save(founded);
    }

}
