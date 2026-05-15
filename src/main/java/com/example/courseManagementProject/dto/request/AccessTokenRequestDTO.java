package com.example.courseManagementProject.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccessTokenRequestDTO {
    @NotBlank
    private String refreshToken;
}
