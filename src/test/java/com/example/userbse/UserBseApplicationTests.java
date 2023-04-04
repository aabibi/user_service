package com.example.userbse;

import com.example.userbse.entity.Users;
import com.example.userbse.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class UserBseApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void testAddUser() {

        Users user  = new Users();
        user.setLastName("Abibi");
        user.setFirstName("Armel");
        userService.addUser(user);

        Assertions.assertEquals(1L, userService.getUserById(1).getId());

    }

    @Test
    void testGetUser() {

        Assertions.assertEquals(1L, userService.getUserById(1).getId());
    }

    @Test
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

    }



}
