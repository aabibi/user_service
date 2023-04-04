package com.example.userbse.exceptions;

import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;

public class InvalidUserException extends Exception {

    public InvalidUserException(String msg) {
        super(msg);
    }
}
