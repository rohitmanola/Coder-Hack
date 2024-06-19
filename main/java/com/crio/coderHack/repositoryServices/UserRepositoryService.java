package com.crio.coderHack.repositoryServices;

import java.util.List;

import com.crio.coderHack.dto.User;
import com.crio.coderHack.exceptions.UserNotFoundException;

public interface UserRepositoryService {
    User createUser(String username);

    User updateScore(String userId, int score) throws UserNotFoundException;

    User findUser(String userId) throws UserNotFoundException;

    List<User> findAllUsers();

    void deleteUser(String userId) throws UserNotFoundException;
}