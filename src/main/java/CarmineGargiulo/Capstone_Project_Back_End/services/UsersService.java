package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.dto.ErrorResponseDTO;
import CarmineGargiulo.Capstone_Project_Back_End.dto.ModifyUserDTO;
import CarmineGargiulo.Capstone_Project_Back_End.dto.ReferenceRecipeDTO;
import CarmineGargiulo.Capstone_Project_Back_End.dto.UserDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.Recipe;
import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.NotFoundException;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.UsersRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private RecipesService recipesService;

    @Autowired
    private Cloudinary cloudinary;

    public Page<User> getAllUsers(int page, int size, String sortBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return usersRepository.findAll(pageable);
    }

    public User getUserById(long userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " " +
                "not found"));
    }

    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User with email " + email + " not found"));
    }

    public User saveUser(UserDTO body) {
        if (usersRepository.existsByEmail(body.email()))
            throw new BadRequestException("Email " + body.email() + " already in use");
        User user = new User(body.name(), body.surname(), body.email(), bcrypt.encode(body.password()));
        return usersRepository.save(user);
    }

    public ErrorResponseDTO addOrRemoveFavourite(ReferenceRecipeDTO body, User logged) {
        String result;
        if (logged.getFavouriteRecipes().stream().anyMatch(recipe -> recipe.getReference() == body.reference())) {
            logged.removeFavourite(body.reference());
            result = "Removed";
        } else {
            Recipe recipe = recipesService.getRecipeByReference(body.reference());
            logged.addFavourite(recipe);
            result = "Added";
        }
        usersRepository.save(logged);
        return new ErrorResponseDTO(result, LocalDateTime.now());
    }

    public User modifyLoggedUser(User logged, ModifyUserDTO body) {
        logged.setName(body.name());
        logged.setSurname(body.surname());
        return usersRepository.save(logged);
    }

    public void uploadAvatar(MultipartFile file, User logged) {
        try {
            String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            logged.setAvatarUrl(url);
            usersRepository.save(logged);
        } catch (IOException e) {
            throw new BadRequestException("File provided not supported");
        }
    }
}
