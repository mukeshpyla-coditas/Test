package com.example.courseManagementProject.service.interfaces;

import com.example.courseManagementProject.dto.request.CourseRegisterRequestDTO;
import com.example.courseManagementProject.dto.response.CourseRegisterResponseDTO;
import com.example.courseManagementProject.dto.response.FetchAllCoursesResponseDTO;

public interface CourseService {
    CourseRegisterResponseDTO registerCourse(CourseRegisterRequestDTO request);
    FetchAllCoursesResponseDTO fetchAllCourses();
}
