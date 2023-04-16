package com.example.userbse;

import com.example.userbse.controller.UserController;
import com.example.userbse.entity.Users;
import com.example.userbse.entity.model.UserResponse;
import com.example.userbse.exceptions.UserNotFoundException;
import com.example.userbse.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class UserBseApplicationTests {


    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void testGetUserById_ValidUserId_UserFound() throws UserNotFoundException {
        // Prepare test data
        long userId =123;
        Users user = new Users();
        user.setId(123);
        Mockito.when(userService.getUserById(123)).thenReturn(user);

        // Perform the test
        UserResponse response = userController.getUserByID(userId);

        // Assert the results
        Assert.assertEquals(user.getId(), response.getId());
    }

    @Test
    public void testGetUserById_InvalidUserId_UserNotFound()  {
        // Prepare test data
        long userId = 456;
        Mockito.when(userService.getUserById(userId)).thenReturn(null);

        // Perform the test
        UserResponse response = null;
       try {
      response = userController.getUserByID(userId);
      } catch (Exception e) {
           // Assert the results
           // Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
           Assert.assertNull(response);
       }
    }



    @Test
    void contextLoads() {
    }



/*    @Test
    void testRemoveUser() {

        userService.deleteUSerById(1);
        try {
            Assertions.assertNull(userService.getUserById(1));
        }
        catch (Exception e) {
              e.printStackTrace();
        }
    }


    @Test
    void testAdExistingUser() {

        Users user  = new Users();
        user.setLastName("John");
        user.setFirstName("Choe");
        userService.addUser(user);



        Users u = userService.findUserBYFirstAndLastName(user.getFirstName(), user.getLastName());
        Assertions.assertNotNull(u);

    }*/



}
