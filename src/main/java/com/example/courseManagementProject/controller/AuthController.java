package com.example.courseManagementProject.controller;

import com.example.courseManagementProject.dto.request.LoginRequestDTO;
import com.example.courseManagementProject.dto.request.RegisterRequestDTO;
import com.example.courseManagementProject.dto.response.LoginResponseDTO;
import com.example.courseManagementProject.dto.response.RegisterResponseDTO;
import com.example.courseManagementProject.service.interfaces.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication APIs")
public class AuthController {
    public final AuthService authService;

    @Operation(
            summary = "Registers a user",
            description = "Accepts the basic details of the user and registers the user. User must login after registering."
    )
    @PostMapping("/signup")
    public RegisterResponseDTO registerUser(@RequestBody @Valid RegisterRequestDTO request) {
        return authService.registerUser(request);
    }

    @Operation(
            summary = "Logs in the registered user",
            description = "Accepts the username and password of the user, which was given at the time of registration."
    )
    @PostMapping("/signin")
    public LoginResponseDTO loginUser(@RequestBody @Valid LoginRequestDTO request) {
        return authService.loginUser(request);
    }
}
