package CarmineGargiulo.Capstone_Project_Back_End.services;

import CarmineGargiulo.Capstone_Project_Back_End.entities.User;
import CarmineGargiulo.Capstone_Project_Back_End.exceptions.NotFoundException;
import CarmineGargiulo.Capstone_Project_Back_End.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public Page<User> getAllUsers(int page, int size, String sortBy){
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return usersRepository.findAll(pageable);
    }

    public User getUserById(long userId){
        return usersRepository.findById(userId).orElseThrow(()->new NotFoundException("User with id " + userId + " not found"));
    }

}
