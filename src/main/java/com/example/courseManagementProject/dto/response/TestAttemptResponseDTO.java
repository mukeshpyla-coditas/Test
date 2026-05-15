package com.example.courseManagementProject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TestAttemptResponseDTO {
    private String courseName;
    private String testStatus;
    private String message;
}
