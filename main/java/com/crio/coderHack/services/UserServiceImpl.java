package com.crio.coderHack.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio.coderHack.dto.User;
import com.crio.coderHack.exceptions.UserNotFoundException;
import com.crio.coderHack.repositoryServices.UserRepositoryService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Override
    public User registerUser(String username) {
        User user = userRepositoryService.createUser(username);
        return user;
    }

    @Override
    public User updateScore(String userId, int score) throws UserNotFoundException {
        User user = userRepositoryService.updateScore(userId, score);
        return user;
    }

    @Override
    public User findUser(String userId) throws UserNotFoundException {
        User user = userRepositoryService.findUser(userId);
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userRepositoryService.findAllUsers();
        sortUsersBasedOnScore(users);
        return users;
    }

    @Override
    public void deregisterUser(String userId) throws UserNotFoundException {
        userRepositoryService.deleteUser(userId);
    }

    private void sortUsersBasedOnScore(List<User> users) {
        Collections.sort(users, (user1, user2) -> user2.getScore() - user1.getScore()); // sorting users based on score in descending order
    }

    
}