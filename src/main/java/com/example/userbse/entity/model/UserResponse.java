package com.example.userbse.entity.model;

import com.example.userbse.entity.Users;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserResponse implements Serializable {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private long id;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserResponse(Users user) {
        this.firstName = user.getFirstName();
        this.lastName  = user.getLastName();
        this.id        = user.getId();
    }
}
