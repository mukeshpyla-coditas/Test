package com.example.courseManagementProject.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccessCourseRequestDTO {
    @NotNull
    private Integer courseId;

    @NotNull
    private Integer lectureId;
}
