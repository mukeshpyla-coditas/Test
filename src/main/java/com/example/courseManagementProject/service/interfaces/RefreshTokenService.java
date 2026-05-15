package com.example.courseManagementProject.service.interfaces;

import com.example.courseManagementProject.dto.request.AccessTokenRequestDTO;
import com.example.courseManagementProject.dto.response.AccessTokenResponseDTO;
import com.example.courseManagementProject.entity.User;

public interface RefreshTokenService {
    String generateRefreshToken(User user);
    AccessTokenResponseDTO generateAccessToken(AccessTokenRequestDTO refreshToken);
}
