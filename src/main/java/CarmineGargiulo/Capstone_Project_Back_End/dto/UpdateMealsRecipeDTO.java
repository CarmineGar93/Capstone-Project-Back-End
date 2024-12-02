package CarmineGargiulo.Capstone_Project_Back_End.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateMealsRecipeDTO(@NotNull(message = "Reference must by provided")
                                   long reference) {
}
