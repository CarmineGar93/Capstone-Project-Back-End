package CarmineGargiulo.Capstone_Project_Back_End.dto;

import jakarta.validation.constraints.NotNull;

public record ReferenceRecipeDTO(@NotNull(message = "Reference must by provided")
                                   long reference) {
}
