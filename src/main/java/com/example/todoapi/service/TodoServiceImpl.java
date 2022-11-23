package com.example.todoapi.service;

import com.example.todoapi.model.TodoEntity;
import com.example.todoapi.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        entity.setPosition(repository.countByUserId(entity.getUserId()));
        repository.save(entity);
        log.info ("Entity Id {} is saved. ", entity.getId());
        return repository.findByUserIdOrderByPosition(entity.getUserId());
    }

    @Override
    public List<TodoEntity> retrieve(String userId) {
        return repository.findByUserIdOrderByPosition(userId);
    }

    @Override
    public List<TodoEntity> update(TodoEntity entity) {
        //Validations
        validata(entity);

        Optional<TodoEntity> original = repository.findById(entity.getId());
        if(original.isPresent()){
            TodoEntity todo = original.get();
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            repository.save(todo);
            log.info ("Entity Id {} is Update. ", entity.getId());
        }

        return retrieve(entity.getUserId());
    }

    @Override
    public List<TodoEntity> delete(TodoEntity entity) {
        validata(entity);
        try{
            log.info ("Entity Id {} is Delete. ", entity.getId());
            repository.delete(entity);
        }catch (Exception e){
            log.error("error deleting entity",entity.getId(),e);
            throw new RuntimeException("error deleting entity" + e.getMessage());
        }
        return retrieve(entity.getUserId());
    }


    @Override
    public List<TodoEntity> changePosition(TodoEntity entity) {
        validata(entity);
        Optional<TodoEntity> original = repository.findById(entity.getId());
        if(original.isPresent()){
            TodoEntity todo = original.get();
            Long oldPosition = todo.getPosition();
            Long newPosition = entity.getPosition();

            repository.decrementBelow(oldPosition,todo.getId());
            repository.incrementBelow(newPosition,todo.getId());

            todo.setPosition(entity.getPosition());

            repository.save(todo);
            log.info ("Entity LIst is Value :: " + retrieve(entity.getUserId()));
        }
        return retrieve(entity.getUserId());
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
