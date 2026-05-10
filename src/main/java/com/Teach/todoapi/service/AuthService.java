package com.Teach.todoapi.service;

import com.Teach.todoapi.dto.request.LoginRequestDTO;
import com.Teach.todoapi.dto.request.RegisterRequestDTO;
import com.Teach.todoapi.dto.response.AuthResponseDTO;
import com.Teach.todoapi.exception.UsernameAlreadyExistsException;
import com.Teach.todoapi.model.User;
import com.Teach.todoapi.repository.UserRepository;
import com.Teach.todoapi.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDTO register(RegisterRequestDTO dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new UsernameAlreadyExistsException("Username already taken: " + dto.username());
        }

        User user = dto.dtoToModel(passwordEncoder.encode(dto.password()));
        userRepository.save(user);

        return new AuthResponseDTO(jwtService.generateToken(user));
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );

        User user = userRepository.findByUsername(dto.username()).orElseThrow();
        return new AuthResponseDTO(jwtService.generateToken(user));
    }
}
