package com.example.courseManagementProject.service.interfaces;

import com.example.courseManagementProject.dto.request.LoginRequestDTO;
import com.example.courseManagementProject.dto.request.RegisterRequestDTO;
import com.example.courseManagementProject.dto.response.LoginResponseDTO;
import com.example.courseManagementProject.dto.response.RegisterResponseDTO;

public interface AuthService {
    RegisterResponseDTO registerUser(RegisterRequestDTO request);
    LoginResponseDTO loginUser(LoginRequestDTO request);
}
