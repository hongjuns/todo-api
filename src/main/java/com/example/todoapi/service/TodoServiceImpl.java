package com.example.todoapi.service;

import com.example.todoapi.model.TodoEntity;
import com.example.todoapi.persistence.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService{
    private final TodoRepository repository;

    public TodoServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public String testTodoData() throws Exception {
        TodoEntity entity = TodoEntity.builder().title("My Test todo item").build();
        repository.save(entity);
        TodoEntity saveEntity = repository.findById(entity.getId()).get();
        return saveEntity.getTitle();
    }
}
