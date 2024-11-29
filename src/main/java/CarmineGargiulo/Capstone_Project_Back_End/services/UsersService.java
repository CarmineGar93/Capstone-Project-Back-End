package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.dto.UserDTO;
import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.BadRequestException;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.NotFoundException;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public Page<User> getAllUsers(int page, int size, String sortBy){
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return usersRepository.findAll(pageable);
    }

    public User getUserById(long userId){
        return usersRepository.findById(userId).orElseThrow(()->new NotFoundException("User with id " + userId + " not found"));
    }

    public User getUserByEmail(String email){
        return usersRepository.findByEmail(email).orElseThrow(()->new NotFoundException("User with email " + email + " not found"));
    }

    public User saveUser(UserDTO body){
        if(usersRepository.existsByEmail(body.email())) throw new BadRequestException("Email " + body.email() + " already in use");
        User user = new User(body.name(), body.surname(), body.email(), bcrypt.encode(body.password()));
        return usersRepository.save(user);
    }

}