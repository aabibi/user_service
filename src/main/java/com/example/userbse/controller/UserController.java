package com.example.userbse.controller;

import com.example.userbse.entity.UserRequest;
import com.example.userbse.entity.Users;
import com.example.userbse.entity.model.UserResponse;
import com.example.userbse.exceptions.InvalidUserException;
import com.example.userbse.exceptions.UserAlreadyExistException;
import com.example.userbse.exceptions.UserNotFoundException;
import com.example.userbse.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "New User", notes = "Add a new user to the system.")
    @PostMapping()
    public ResponseEntity<Long> addUser(@RequestBody  @Valid  UserRequest userRequest) throws  UserAlreadyExistException {


        if (!ObjectUtils.isEmpty(userService.findUserBYFirstAndLastName(userRequest.getFirstName(), userRequest.getLastName()))) {
            throw  new UserAlreadyExistException("User already exist. Aborting adding user.");
        }

        Long newUserId =  userService.createUser(userRequest);
        return new ResponseEntity<>(newUserId, HttpStatus.OK);
    }

/*    @ApiOperation(value = "New User", notes = "Add a new user to the system.")
    @PostMapping("users")
    public UserResponse addUser(@RequestBody  @Valid  UserRequest userRequest) throws  UserAlreadyExistException {


        if (!ObjectUtils.isEmpty(userService.findUserBYFirstAndLastName(userRequest.getFirstName(), userRequest.getLastName()))) {
            throw  new UserAlreadyExistException("User already exist. Aborting adding user.");
        }


        Users user = new Users();
        user.setFirstName(userRequest.getFirstName().toLowerCase());
        user.setLastName(userRequest.getLastName().toLowerCase());


        ////  For Testing JVM with Visualvm
*//*        log.info("Adding 10000 users on every requests....");

        List<Users> usersList = new ArrayList<>();
        for ( int i=0; i <= 100000000; i++) {
            usersList.add(new Users());

        }*//*

     Users users =  userService.addUser(user);
     return new UserResponse(users);
    }*/

    @ApiOperation(value = "User id", notes = "Get the user information which belond to the userid passed in")
    @GetMapping("{userId}")
    @Cacheable(key ="#userId", value ="Users")
    public ResponseEntity<UserResponse> getUserByID(@PathVariable long userId)  {

             log.info(" Getting user from DB");
             Users user = userService.getUserById(userId);
             return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
    }



    @ApiOperation(value = "N/A", notes = "Get all user info from the system")
    @GetMapping()
    public ResponseEntity<List<UserResponse>> getallUsers()  {

        log.info(" Getting All users from DB");

        List<Users> users = userService.getAllUsers();

        if (users == null) {
            throw new UserNotFoundException("No users found in the system.");
        }

        List<UserResponse> userResponses = new ArrayList<>();
        users.forEach( u -> userResponses.add(new UserResponse(u)));

        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }


    @ApiOperation(value = "User id", notes = "Delete user information which belong to the userid passed in")
    @DeleteMapping("{userId}")
    @CacheEvict(key = "#userId",value = "Users")
    public ResponseEntity<Long> deleteUser(@PathVariable long userId)  {

        Users user = userService.getUserById(userId);

        if (user == null) {
            throw new UserNotFoundException("User with id " + userId  + " not found.");
        }

        userService.deleteUSerById(userId);
        return  new ResponseEntity<>(userId, HttpStatus.OK); // "success,  user has been deleted.";
    }




    @ApiOperation(value = "Updated User", notes = "Update a user in the system.")
    @PutMapping()
    public String updateUser(@RequestBody UserRequest userRequest) throws InvalidUserException, UserNotFoundException {

        if (!validateUser(userRequest)) {
            throw  new InvalidUserException("Incorrect User parameters. Cannot update record");
        }

        Users user = userService.findUserBYFirstAndLastName(userRequest.getFirstName(), userRequest.getLastName());
        if (ObjectUtils.isEmpty(user)){
            throw  new UserNotFoundException("User Does not exist. Aborting updating user.");
        }

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        userService.addUser(user);

        return "success, user has been update.";
    }

    @ApiOperation(value = "Updated User", notes = "Update a user in the system.")
    @PutMapping("{userId}")
    @CachePut(key = "#userId",value = "Users")
    public UserResponse updateUserV2(@PathVariable long userId, @RequestBody @Valid  UserRequest userRequest) throws  UserNotFoundException {

        Users user = userService.getUserById(userId);
        userService.updateUser(userId, userRequest);
        return new UserResponse(user);
    }

    @ApiOperation(value = "Updated user fields", notes = "Update a user field in the system, instead of sending the whole object.")
    @PatchMapping("{userId}")
    @CachePut(key = "#userId",value = "Users")
    public UserResponse updateUserFields(@PathVariable long userId, @RequestBody Map<String, Object> fields)  {

        Users user = userService.updateProductByFields(userId, fields);
        return new UserResponse(user);
    }

    private boolean validateUser( UserRequest userRequest) {

        if ( userRequest != null) {
            return !ObjectUtils.isEmpty(userRequest.getFirstName())  &&  !ObjectUtils.isEmpty(userRequest.getLastName());
        }

        return  false;
    }

}
