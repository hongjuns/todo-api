package com.example.todoapi.controller;

import com.example.todoapi.dto.TodoDTO;
import com.example.todoapi.model.ResponseDTO;
import com.example.todoapi.model.TodoEntity;
import com.example.todoapi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @PostMapping
    public ResponseEntity<?> createTodo (@RequestBody TodoDTO dto) throws Exception{
        try{
            String tempUserId = "temp-user";

            TodoEntity entity = TodoDTO.toEntity(dto);

            entity.setId(null);
            entity.setUserId(tempUserId);
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



}
