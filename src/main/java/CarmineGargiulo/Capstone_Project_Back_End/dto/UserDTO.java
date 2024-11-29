package CarmineGargiulo.Capstone_Project_Back_End.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDTO(@NotEmpty(message = "Password must be provided")
                      @Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=]).*$",
                              message = "Password must have at least 8 characters, one must be a number , one in " +
                                      "lower case, one in" +
                                      " upper case and one special character @#!$%^&+=")
                      String password,
                      @NotEmpty(message = "Email must be provided")
                      @Email(message = "Invalid email")
                      String email,
                      @NotEmpty(message = "Name must be provided")
                      @Size(min = 2, max = 40, message = "Name size must be between 2 and 40")
                      String name,
                      @NotEmpty(message = "Surname must be provided")
                      @Size(min = 2, max = 40, message = "Surname size must be between 2 and 40")
                      String surname) {
}
