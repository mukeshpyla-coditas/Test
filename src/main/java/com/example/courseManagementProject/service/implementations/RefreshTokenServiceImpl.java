package com.example.courseManagementProject.service.implementations;

import com.example.courseManagementProject.dto.request.AccessTokenRequestDTO;
import com.example.courseManagementProject.dto.response.AccessTokenResponseDTO;
import com.example.courseManagementProject.entity.RefreshToken;
import com.example.courseManagementProject.entity.User;
import com.example.courseManagementProject.exception.EntityNotFoundException;
import com.example.courseManagementProject.exception.TokenExpirationException;
import com.example.courseManagementProject.repository.RefreshTokenRepository;
import com.example.courseManagementProject.repository.UserRepository;
import com.example.courseManagementProject.service.interfaces.RefreshTokenService;
import com.example.courseManagementProject.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public String generateRefreshToken(User user) {
        String refreshToken = UUID.randomUUID().toString();
        RefreshToken refreshToken1 = RefreshToken.builder()
                .refreshToken(refreshToken)
                .user(user)
                .expirationTime(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .build();
        refreshTokenRepository.save(refreshToken1);

        return refreshToken;
    }

    @Override
    public AccessTokenResponseDTO generateAccessToken(AccessTokenRequestDTO refreshToken) {
        RefreshToken existingRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken.getRefreshToken());
        String accessToken = null;
        if(existingRefreshToken.getExpirationTime().after(new Date())) {
            User existingUser = userRepository.findById(existingRefreshToken.getUser().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Specified user is not found"));
            accessToken = jwtUtil.generateToken(existingUser.getUsername(), existingUser.getRole().toString());
        }
        else {
            refreshTokenRepository.delete(existingRefreshToken);
            throw new TokenExpirationException("Given refresh token is expired. Please re-login.");
        }

        return AccessTokenResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
