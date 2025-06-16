package com.pulicies.sapient.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
     @ExceptionHandler(RecipeNotFoundException.class)
     public ResponseEntity<Map<String, Object>> handleReceipeNotFound(RecipeNotFoundException ex){
        Map<String,Object> response = new HashMap<>();
        response.put("timeStamp",LocalDateTime.now());
        response.put("status",HttpStatus.NOT_FOUND.value());
        response.put("error","Not Found");
        response.put("messsage",ex.getMessage());

        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
     }


}
