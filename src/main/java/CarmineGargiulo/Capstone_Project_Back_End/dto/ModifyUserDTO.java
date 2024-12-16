package CarmineGargiulo.Capstone_Project_Back_End.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ModifyUserDTO(@NotEmpty(message = "Name must be provided")
                            @Size(min = 2, max = 40, message = "Name size must be between 2 and 40")
                            String name,
                            @NotEmpty(message = "Surname must be provided")
                            @Size(min = 2, max = 40, message = "Surname size must be between 2 and 40")
                            String surname) {
}
