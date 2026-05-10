package com.Teach.todoapi.dto.request;

import com.Teach.todoapi.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50)
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password
) {
    public User dtoToModel(String encodedPassword) {
        return new User(this.username(), encodedPassword);
    }
}
