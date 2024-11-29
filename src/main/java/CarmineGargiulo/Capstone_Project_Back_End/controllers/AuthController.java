package CarmineGargiulo.Capstone_Project_Back_End.controllers;

import CarmineGargiulo.Capstone_Project_Back_End.dto.UserDTO;
import CarmineGargiulo.Capstone_Project_Back_End.dto.UserLoginDTO;
import CarmineGargiulo.Capstone_Project_Back_End.dto.UserLoginTokenResponseDTO;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserLoginTokenResponseDTO registerUser(@RequestBody @Validated UserDTO body, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String message = bindingResult.getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return new UserLoginTokenResponseDTO(authService.register(body));
    }

    @PostMapping("/login")
    public UserLoginTokenResponseDTO loginUser(@RequestBody @Validated UserLoginDTO body, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String message = bindingResult.getAllErrors().stream().map(e->e.getDefaultMessage()).collect(Collectors.joining(", "));
            throw new BadRequestException(message);
        }
        return new UserLoginTokenResponseDTO(authService.generateToken(body));
    }
}
