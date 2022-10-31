package com.example.todoapi.controller;

import com.example.todoapi.dto.ResponseDTO;
import com.example.todoapi.dto.TodoDTO;
import com.example.todoapi.model.TodoEntity;
import com.example.todoapi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @PostMapping("/create")
    public ResponseEntity<?> createTodo (@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) throws Exception{
        try{
            System.out.println("UserID : " + userId);
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setId(null);
            entity.setUserId(userId);

            List<TodoEntity> entities = service.create(entity);
            List<TodoDTO> dtos = entities.stream().map(entityData -> new TodoDTO(entityData)).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/select")
    public ResponseEntity<?> selectTodo (@AuthenticationPrincipal String userId) {
        System.out.println("UserID : " + userId);
        List<TodoEntity> entities = service.retrieve(userId);
        List <TodoDTO> dtos = entities.stream().map(entityData -> new TodoDTO(entityData)).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTodo (@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto) {

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(userId);

        List<TodoEntity> entities = service.update(entity);
        List <TodoDTO> dtos = entities.stream().map(entityData -> new TodoDTO(entityData)).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTodo (@AuthenticationPrincipal String userId,@RequestBody TodoDTO dto) {
        try{

            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(userId);

            List<TodoEntity> entities = service.delete(entity);
            List <TodoDTO> dtos = entities.stream().map(entityData -> new TodoDTO(entityData)).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        }catch (Exception e){
            String error =e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);

        }

    }

}
