package com.example.courseManagementProject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class GetProgressOfCourseResponseDTO {
    private String username;
    private String courseName;
    private Double percentageCompleted;
}
