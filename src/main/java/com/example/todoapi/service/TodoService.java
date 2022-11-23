package com.example.todoapi.service;

import com.example.todoapi.model.TodoEntity;

import java.util.List;

public interface TodoService {
     List<TodoEntity> create (TodoEntity entity);
     List<TodoEntity> retrieve (String userId);
     List<TodoEntity> update (TodoEntity entity);
     List<TodoEntity> delete (TodoEntity entity);
     List<TodoEntity> changePosition (TodoEntity entity);
}

