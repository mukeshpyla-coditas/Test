package com.example.courseManagementProject.service.interfaces;

import com.example.courseManagementProject.dto.request.AccessCourseRequestDTO;
import com.example.courseManagementProject.dto.request.EnrollmentRequestDTO;
import com.example.courseManagementProject.dto.response.AccessCourseResponseDTO;
import com.example.courseManagementProject.dto.response.EnrollmentResponseDTO;
import com.example.courseManagementProject.dto.response.FetchAllCoursesResponseDTO;
import com.example.courseManagementProject.dto.response.GetProgressOfCourseResponseDTO;

public interface UserService {
    EnrollmentResponseDTO enrollUserToCourse(EnrollmentRequestDTO request);
    FetchAllCoursesResponseDTO fetchEnrolledCourses();
    AccessCourseResponseDTO accessEnrolledCourse(AccessCourseRequestDTO request);
    GetProgressOfCourseResponseDTO getProgress(Integer courseId);
}
