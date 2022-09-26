package com.example.todoapi.service;

import com.example.todoapi.model.TodoEntity;
import com.example.todoapi.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class TodoServiceImpl implements TodoService{
    private final TodoRepository repository;

    public TodoServiceImpl(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TodoEntity> create(TodoEntity entity) {

        //Validations
        validata(entity);
        //Save
        repository.save(entity);
        log.info ("Entity Id {} is saved. ", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    private void validata(TodoEntity entity){
        if (entity == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null.");
        }
        if(entity.getUserId() == null){
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }
}
