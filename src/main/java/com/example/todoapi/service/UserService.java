package com.example.todoapi.service;

import com.example.todoapi.model.UserEntity;

public interface UserService {
    UserEntity create(UserEntity userEntity);
    UserEntity getByCredentials (String email, String password);
}
