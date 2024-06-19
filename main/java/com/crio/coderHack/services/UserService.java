package com.crio.coderHack.services;

import java.util.List;

import com.crio.coderHack.dto.User;
import com.crio.coderHack.exceptions.UserNotFoundException;

public interface UserService {
    User registerUser(String username);

    User updateScore(String userId, int score) throws UserNotFoundException;

    User findUser(String userId) throws UserNotFoundException;

    List<User> findAllUsers();

    void deregisterUser(String userId) throws UserNotFoundException;
}