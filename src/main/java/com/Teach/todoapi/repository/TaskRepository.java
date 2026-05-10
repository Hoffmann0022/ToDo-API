package com.Teach.todoapi.repository;

import com.Teach.todoapi.model.Task;
import com.Teach.todoapi.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByUserId(Long userId);
    List<Task> findAllByUserIdAndStatus(Long userId, TaskStatus status);
    Optional<Task> findByIdAndUserId(Long id, Long userId);
}
