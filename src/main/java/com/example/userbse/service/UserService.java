package com.example.userbse.service;

import com.example.userbse.entity.UserRequest;
import com.example.userbse.entity.Users;
import com.example.userbse.exceptions.UserNotFoundException;
import com.example.userbse.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

private  final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<Users> getAllUsers() {
        return userRepository.findAll();

    }

    public Users findUserBYFirstAndLastName(String firstName, String lastName) {

        return  userRepository.findUsersByFirstNameAndLastName(firstName,lastName);

    }
   public Users getUserById(long id)  {

        Optional<Users> u  = userRepository.findById(id);
        return  u.orElse(null);// u.orElseThrow(() -> new EntityNotFoundException("User with id "+ id  + " not found"));
   }

   public void  deleteUSerById(long id) {

       Optional<Users> u  = userRepository.findById(id);
       u.ifPresent(userRepository::delete);
   }

   public Users addUser (Users u)  {
       return userRepository.save(u);
   }

    public void updateUser (long id, UserRequest  u) throws UserNotFoundException {
        Optional<Users> exisingUser = userRepository.findById(id);
        if (exisingUser.isPresent()) {
           Users user = exisingUser.get();
            user.setLastName(u.getLastName());
            user.setFirstName(u.getFirstName());
            userRepository.save(user);
        } else {
            throw  new UserNotFoundException("User Does not exist. Aborting updating user.");
        }
    }

    public Users updateProductByFields(long id, Map<String, Object> fields) {
        Optional<Users> exisingUser = userRepository.findById(id);

        if (exisingUser.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Users.class, key);
                Objects.requireNonNull(field).setAccessible(true);
                ReflectionUtils.setField(field, exisingUser.get(), value);
            });
            return   userRepository.save(exisingUser.get());
        }
        return null;
    }
}
