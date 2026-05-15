package com.example.courseManagementProject.dto.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class LectureRegisterRequestDTO {
    @NonNull
    private Integer courseId;

    @Column(nullable = false, unique = true)
    private String lectureName;

    @NonNull
    private String lectureDescription;

}
