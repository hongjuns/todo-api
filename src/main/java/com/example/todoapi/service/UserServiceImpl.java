package com.example.todoapi.service;

import com.example.todoapi.model.UserEntity;
import com.example.todoapi.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @Override
    public UserEntity create(UserEntity userEntity) {
        if(userEntity == null || userEntity.getEmail() == null){
            throw new RuntimeException("Invalid arguments");
        }
        String email = userEntity.getEmail();
        if(userRepository.existsByEmail(email)){
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
