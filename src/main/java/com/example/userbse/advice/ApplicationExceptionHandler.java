package com.example.userbse.advice;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidParametersArgument(
            Exception ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        MethodArgumentNotValidException exception = (MethodArgumentNotValidException)ex;
        exception.getBindingResult().getFieldErrors().forEach(error ->  {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return errorMap;
    }


}
