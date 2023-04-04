package com.example.userbse.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(description = "Request object for POST method")
public class UserRequest {

    @ApiModelProperty(required = true, notes = "First name of the user")
    @NotNull(message = "First Name should not be null")
    private String firstName;

    @ApiModelProperty(required = true, notes = "Last name of the user")
    @NotNull(message = "Last Name should not be null")
    private String lastName;

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
}
