package CarmineGargiulo.Capstone_Project_Back_End.tools;

import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import kong.unirest.core.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpoonacularSender {
    private String apiKey;

    public SpoonacularSender(@Value("${spoon.apikey}") String apiKey) {
        this.apiKey = apiKey;
    }

    public JSONObject getRecipeInfo(long reference) {
        HttpResponse<JsonNode> response = Unirest.get("https://api.spoonacular.com/recipes/" + reference +
                        "/information")
                .queryString("includeNutrition", true)
                .header("x-api-key", this.apiKey)
                .asJson();
        return response.getBody().getObject();
    }

    public JSONObject getRecipesByQuery(String query) {
        HttpResponse<JsonNode> response = Unirest.get("https://api.spoonacular.com/recipes/complexSearch")
                .queryString("query", query)
                .queryString("number", 100)
                .header("x-api-key", this.apiKey)
                .asJson();
        return response.getBody().getObject();
    }
}
