package com.Teach.todoapi.dto.response;

import com.Teach.todoapi.model.Task;
import com.Teach.todoapi.model.TaskStatus;

public record TaskResponseDTO(Long id, String title, String description, TaskStatus status, Long userId) {
    public static TaskResponseDTO modelToDto(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getUser().getId()
        );
    }
}
