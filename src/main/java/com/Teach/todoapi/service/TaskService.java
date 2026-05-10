package com.Teach.todoapi.service;

import com.Teach.todoapi.dto.request.TaskPatchDTO;
import com.Teach.todoapi.dto.request.TaskRequestDTO;
import com.Teach.todoapi.dto.response.TaskResponseDTO;
import com.Teach.todoapi.exception.NotFoundException;
import com.Teach.todoapi.model.Task;
import com.Teach.todoapi.model.TaskStatus;
import com.Teach.todoapi.model.User;
import com.Teach.todoapi.repository.TaskRepository;
import com.Teach.todoapi.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDTO> findAll(Long userId, TaskStatus status) {
        List<Task> list = (status != null)
                ? taskRepository.findAllByUserIdAndStatus(userId, status)
                : taskRepository.findAllByUserId(userId);

        return list.stream().map(TaskResponseDTO::modelToDto).toList();
    }

    public TaskResponseDTO findById(Long id, Long userId) {
        Task task = search(id, userId);
        return TaskResponseDTO.modelToDto(task);
    }

    public TaskResponseDTO create(TaskRequestDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Task task = taskRepository.save(dto.dtoToModel(user));
        return TaskResponseDTO.modelToDto(task);
    }

    public TaskResponseDTO update(Long id, TaskPatchDTO dto, Long userId) {
        Task task = search(id, userId);

        if (dto.description() != null) {
            task.setDescription(dto.description());
        }
        if (dto.status() != null) {
            task.setStatus(dto.status());
        }

        return TaskResponseDTO.modelToDto(taskRepository.save(task));
    }

    public void delete(Long id, Long userId) {
        Task task = search(id, userId);
        taskRepository.delete(task);
    }

    private Task search(Long id, Long userId) {
        return taskRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " + id));
    }
}
