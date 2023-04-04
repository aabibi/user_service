package com.example.userbse.exceptions;

import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;

public class DataBaseException extends ConstraintViolationException {


    public DataBaseException(String message, SQLException root, String constraintName) {
        super(message, root, constraintName);
    }

    public DataBaseException(String message, SQLException root, String sql, String constraintName) {
        super(message, root, sql, constraintName);
    }
}
