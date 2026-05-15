package com.example.courseManagementProject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@Builder
public class FetchAllCoursesResponseDTO {
    Map<String, List<String>> courseList;
}
