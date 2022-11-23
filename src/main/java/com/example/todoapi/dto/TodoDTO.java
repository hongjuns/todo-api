package com.example.todoapi.dto;
import com.example.todoapi.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    private String id;
    private String title;
    private boolean done;
    private Long position;

    public TodoDTO(final TodoEntity entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.done=entity.isDone();
        this.position=entity.getPosition();
    }

    public static TodoEntity toEntity(TodoDTO dto){
        return TodoEntity.builder()
                .id(dto.getId())
                .title(dto.title)
                .done(dto.isDone())
                .position(dto.getPosition())
                .build();
    }
}
