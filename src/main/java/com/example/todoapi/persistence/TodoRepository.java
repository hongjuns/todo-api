package com.example.todoapi.persistence;

import com.example.todoapi.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TodoRepository extends JpaRepository <TodoEntity,String> {
}

