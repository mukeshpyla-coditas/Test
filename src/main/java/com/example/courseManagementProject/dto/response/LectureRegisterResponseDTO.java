package com.example.courseManagementProject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LectureRegisterResponseDTO {
    private String courseName;
    private Integer lectureId;
    private String lectureName;
}
