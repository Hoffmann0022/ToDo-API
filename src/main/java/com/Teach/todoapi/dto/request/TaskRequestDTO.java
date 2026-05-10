package com.Teach.todoapi.dto.request;

import com.Teach.todoapi.model.Task;
import com.Teach.todoapi.model.User;
import jakarta.validation.constraints.NotBlank;

public record TaskRequestDTO(
        @NotBlank(message = "Title is required")
        String title,

        String description
) {
    public Task dtoToModel(User user) {
        return new Task(this.title(), this.description(), user);
    }
}
