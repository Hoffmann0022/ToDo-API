package com.Teach.todoapi.controller;

import com.Teach.todoapi.dto.request.TaskPatchDTO;
import com.Teach.todoapi.dto.request.TaskRequestDTO;
import com.Teach.todoapi.dto.response.TaskResponseDTO;
import com.Teach.todoapi.model.TaskStatus;
import com.Teach.todoapi.model.User;
import com.Teach.todoapi.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    @Operation(summary = "List all tasks (optional filter by status)")
    public List<TaskResponseDTO> findAll(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) TaskStatus status
    ) {
        return taskService.findAll(user.getId(), status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id")
    public TaskResponseDTO findById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return taskService.findById(id, user.getId());
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<TaskResponseDTO> create(
            @Valid @RequestBody TaskRequestDTO dto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(dto, user.getId()));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update task status or description")
    public TaskResponseDTO update(
            @PathVariable Long id,
            @RequestBody TaskPatchDTO dto,
            @AuthenticationPrincipal User user
    ) {
        return taskService.update(id, dto, user.getId());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        taskService.delete(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
