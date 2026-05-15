package com.example.courseManagementProject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AccessCourseResponseDTO {
    private String courseName;
    private String lectureName;
    private String message;
}
