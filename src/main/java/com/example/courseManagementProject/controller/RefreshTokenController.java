package com.example.courseManagementProject.controller;

import com.example.courseManagementProject.dto.request.AccessTokenRequestDTO;
import com.example.courseManagementProject.dto.response.AccessTokenResponseDTO;
import com.example.courseManagementProject.service.interfaces.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refresh-token")
@RequiredArgsConstructor
@Tag(name = "RefreshToken API")
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @Operation(
            summary = "Generates AccessToken, by accepting RefreshToken",
            description = "User sends the RefreshToken provided and requests for access token. " +
                    "If the refreshToken is NOT expired, only then we will be re-generating the accessToken."
    )
    @GetMapping
    public AccessTokenResponseDTO getAccessTokenViaRefreshToken(@RequestBody AccessTokenRequestDTO refreshToken) {
        return refreshTokenService.generateAccessToken(refreshToken);
    }
}
