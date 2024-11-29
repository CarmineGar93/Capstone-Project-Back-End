package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.dto.UserDTO;
import CarmineGargiulo.Capstone_Project_Back_End.dto.UserLoginDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.NotFoundException;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.UnauthorizedException;
import CarmineGargiulo.Capstone_Project_Back_End.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private JWT jwt;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private UsersService usersService;

    public String generateToken(UserLoginDTO body){
        try {
            User user = usersService.getUserByEmail(body.email());
            if(bcrypt.matches(body.password(), user.getPassword())) return jwt.generateToken(user);
            else throw new UnauthorizedException("Invalid credentials");
        } catch (NotFoundException e){
            throw new UnauthorizedException("Invalid credentials");
        }

    }

    public String register(UserDTO body){
        User registered = usersService.saveUser(body);
        return jwt.generateToken(registered);
    }
}
