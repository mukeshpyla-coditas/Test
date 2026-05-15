package com.example.courseManagementProject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginResponseDTO {
    private String message;
    private String accessToken;
    private String refreshToken;
}
