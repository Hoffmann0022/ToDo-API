package com.Teach.todoapi.dto.request;

import com.Teach.todoapi.model.TaskStatus;

public record TaskPatchDTO(String description, TaskStatus status) {}
