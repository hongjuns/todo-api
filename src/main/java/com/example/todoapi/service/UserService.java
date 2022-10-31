package com.example.todoapi.service;

import com.example.todoapi.model.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {
    UserEntity create(UserEntity userEntity);
    UserEntity getByCredentials (String email, String password, PasswordEncoder encoder);
}
