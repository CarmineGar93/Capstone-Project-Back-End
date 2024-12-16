package CarmineGargiulo.Capstone_Project_Back_End.controllers;

import CarmineGargiulo.Capstone_Project_Back_End.dto.ErrorResponseDTO;
import CarmineGargiulo.Capstone_Project_Back_End.dto.ModifyUserDTO;
import CarmineGargiulo.Capstone_Project_Back_End.dto.ReferenceRecipeDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Recipe;
import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("/me/favourites")
    public List<Recipe> getFavourites(@AuthenticationPrincipal User logged) {
        return logged.getFavouriteRecipes();
    }

    @GetMapping("/me")
    public User getLoggedUser(@AuthenticationPrincipal User logged) {
        return logged;
    }

    @PatchMapping("/me/favourites")
    public ErrorResponseDTO addOrRemoveFavourite(@AuthenticationPrincipal User logged,
                                                 @RequestBody @Validated ReferenceRecipeDTO body,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return usersService.addOrRemoveFavourite(body, logged);
    }

    @PutMapping("/me")
    public User modifyLoggedUser(@AuthenticationPrincipal User logged, @RequestBody @Validated ModifyUserDTO body,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message =
                    bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.joining(
                            ", "));
            throw new BadRequestException(message);
        }
        return usersService.modifyLoggedUser(logged, body);
    }

    @PatchMapping("/me/avatar")
    public void updateAvatar(@RequestParam("avatar") MultipartFile file, @AuthenticationPrincipal User logged) {
        usersService.uploadAvatar(file, logged);
    }
}
