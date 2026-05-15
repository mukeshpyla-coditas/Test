package com.example.courseManagementProject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RegisterResponseDTO {
    private Integer userId;
    private String userRole;
    private String message;
}
