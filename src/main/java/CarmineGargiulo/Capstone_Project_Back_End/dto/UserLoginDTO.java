package CarmineGargiulo.Capstone_Project_Back_End.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserLoginDTO(@NotEmpty(message = "Password must be provided")
                           String password,
                           @NotEmpty(message = "Email must be provided")
                           @Email(message = "Invalid email")
                           String email) {
}
