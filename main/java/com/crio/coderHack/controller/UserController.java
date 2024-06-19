package com.crio.coderHack.controller;

import java.util.List;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crio.coderHack.dto.User;
import com.crio.coderHack.exceptions.UserNotFoundException;
import com.crio.coderHack.exchanges.RegisterUserRequest;
import com.crio.coderHack.services.UserService;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@Validated
@RestController
public class UserController {
    public static final String USER_API_ENDPOINT = "/users";

    @Autowired
    private UserService userService;

    @GetMapping(USER_API_ENDPOINT)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(USER_API_ENDPOINT + "/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user;

        try {
            user = userService.findUser(userId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(user);
    }

    @PostMapping(USER_API_ENDPOINT) 
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        String username = registerUserRequest.getUsername();

        User user = userService.registerUser(username);

        return ResponseEntity.ok().body(user);
    }

    @PutMapping(USER_API_ENDPOINT + "/{userId}")
    public ResponseEntity<User> updateScore(@PathVariable String userId, @Range(min = 0, max = 100) @RequestParam int score) {
        User user;

        try {
            user = userService.updateScore(userId, score);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(USER_API_ENDPOINT + "/{userId}")
    public ResponseEntity<Void> deregisterUser(@PathVariable String userId) {
        
        try {
            userService.deregisterUser(userId);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body("Bad Request");
  }

}