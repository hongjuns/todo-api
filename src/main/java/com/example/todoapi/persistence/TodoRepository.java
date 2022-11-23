package com.example.todoapi.persistence;

import com.example.todoapi.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository <TodoEntity,String> {
    Long countByUserId(String userId);
    List<TodoEntity> findByUserIdOrderByPosition (String userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Todo SET position = position - 1 WHERE position >= :position AND id <> :id" , nativeQuery = true)
    void decrementBelow (@Param("position") Long position, @Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Todo SET position = position + 1 WHERE position >= :position AND id <> :id", nativeQuery = true)
    void incrementBelow (@Param ("position") Long position , @Param("id") String id);
}

