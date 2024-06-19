package com.crio.coderHack.repositoryServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio.coderHack.dto.Badge;
import com.crio.coderHack.dto.User;
import com.crio.coderHack.exceptions.UserNotFoundException;
import com.crio.coderHack.models.UserEntity;
import com.crio.coderHack.repositories.UserRepository;

import jakarta.inject.Provider;

@Service
public class UserRepositoryServiceImpl implements UserRepositoryService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Provider<ModelMapper> modelMapperProvider;

    @Override
    public User createUser(String username) {
        ModelMapper modelMapper = modelMapperProvider.get();
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        User user = modelMapper.map(userRepository.save(userEntity), User.class);
        return user;
    }

    @Override
    public User updateScore(String userId, int score) throws UserNotFoundException {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<UserEntity> maybeUserEntity = userRepository.findByUserId(userId);

        if(maybeUserEntity.isPresent()) {
            UserEntity userEntity = maybeUserEntity.get();
            userEntity.setScore(score);
            awardBadges(userEntity, score);
            userRepository.save(userEntity);
            return modelMapper.map(userEntity, User.class);
        }
        else 
            throw new UserNotFoundException();
    }

    @Override
    public User findUser(String userId) throws UserNotFoundException {
        ModelMapper modelMapper = modelMapperProvider.get();
        Optional<UserEntity> maybeUserEntity = userRepository.findByUserId(userId);

        if(maybeUserEntity.isPresent()) {
            UserEntity userEntity = maybeUserEntity.get();
            return modelMapper.map(userEntity, User.class);
        }
        else 
            throw new UserNotFoundException();
    }

    @Override
    public List<User> findAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<User> users = convertToUserList(userEntities);
        return users;
    }

    @Override
    public void deleteUser(String userId) throws UserNotFoundException {
        Optional<UserEntity> maybeUserEntity = userRepository.findByUserId(userId);

        if(maybeUserEntity.isPresent()) {
            UserEntity userEntity = maybeUserEntity.get();
            userRepository.delete(userEntity);
        }
        else 
            throw new UserNotFoundException();
    }

    private void awardBadges(UserEntity userEntity, int score) {
        List<Badge> badges = userEntity.getBadges();
        badges.clear();

        if(score >= 60 && score <= 100) {
            badges.add(Badge.CODE_NINJA);
            badges.add(Badge.CODE_CHAMP);
            badges.add(Badge.CODE_MASTER);
        }
        else if(score >= 30 && score < 60) {
            badges.add(Badge.CODE_NINJA);
            badges.add(Badge.CODE_CHAMP);
        }
        else if(score >= 1 && score < 30)
            badges.add(Badge.CODE_NINJA);

    }

    private List<User> convertToUserList(List<UserEntity> userEntities) {
        ModelMapper modelMapper = modelMapperProvider.get();
        List<User> users = new ArrayList<>();
        
        for(UserEntity userEntity : userEntities) {
            User user = modelMapper.map(userEntity, User.class);
            users.add(user);
        }

        return users;
    }

}