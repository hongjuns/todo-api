package com.example.todoapi.service;

import com.example.todoapi.model.TodoEntity;

import java.util.List;

public interface TodoService {
     List<TodoEntity> create (TodoEntity entity);
}

