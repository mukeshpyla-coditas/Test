package com.example.courseManagementProject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CourseRegisterResponseDTO {
    private Integer courseId;
    private String courseName;
    private String message;
}
