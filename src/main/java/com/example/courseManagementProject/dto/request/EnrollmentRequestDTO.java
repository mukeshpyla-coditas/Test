package com.example.courseManagementProject.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnrollmentRequestDTO {
    private Integer userId;
    private Integer courseId;
}
