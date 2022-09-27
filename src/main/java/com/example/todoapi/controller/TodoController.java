package com.example.todoapi.controller;

import com.example.todoapi.dto.ResponseDTO;
import com.example.todoapi.dto.TodoDTO;
import com.example.todoapi.model.TodoEntity;
import com.example.todoapi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/select")
    public ResponseEntity<?> selectTodo () {
        String tempUserId = "temp-user";

        List<TodoEntity> entities = service.retrieve(tempUserId);
        List <TodoDTO> dtos = entities.stream().map(entityData -> new TodoDTO(entityData)).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTodo (@RequestBody TodoDTO dto) {
        String tempUserId = "temp-user";

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(tempUserId);

        List<TodoEntity> entities = service.update(entity);
        List <TodoDTO> dtos = entities.stream().map(entityData -> new TodoDTO(entityData)).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTodo (@RequestBody TodoDTO dto) {
        try{
            String tempUserId = "temp-user";

            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setUserId(tempUserId);

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
