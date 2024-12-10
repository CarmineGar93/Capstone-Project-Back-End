package CarmineGargiulo.Capstone_Project_Back_End.tools;

import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.NotFoundException;
import com.google.gson.Gson;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SpoonacularSender {
    private String apiKey;

    public SpoonacularSender(@Value("${spoon.apikey}") String apiKey) {
        this.apiKey = apiKey;
    }

    public JSONObject getRecipeByReference(long reference) {
        HttpResponse<JsonNode> response = Unirest.get("https://api.spoonacular.com/recipes/" + reference +
                        "/information")
                .queryString("includeNutrition", true)
                .header("x-api-key", this.apiKey)
                .asJson();
        if (response.getBody() == null) throw new BadRequestException("Something went wrong");
        return response.getBody().getObject();
    }

    public JSONObject getRecipesByQuery(String query) {
        HttpResponse<JsonNode> response = Unirest.get("https://api.spoonacular.com/recipes/complexSearch")
                .queryString("query", query)
                .queryString("number", 100)
                .header("x-api-key", this.apiKey)
                .asJson();
        if (response.getBody() == null) throw new NotFoundException("No recipe founded");
        return response.getBody().getObject();
    }

    public HashMap getRecipeInfo(long reference) {
        HashMap response = Unirest.get("https://api.spoonacular.com/recipes/" + reference +
                        "/information")
                .queryString("includeNutrition", true)
                .header("x-api-key", this.apiKey)
                .asObject(i -> new Gson().fromJson(i.getContentReader(), HashMap.class))
                .getBody();
        return response;
    }

    public HashMap getRandomRecipes() {
        HttpResponse<HashMap> response = Unirest.get("https://api.spoonacular.com/recipes/random")
                .queryString("number", 12)
                .header("x-api-key", this.apiKey)
                .asObject(HashMap.class);
        return response.getBody();
    }
}
