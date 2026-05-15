package com.example.courseManagementProject.controller;

import com.example.courseManagementProject.dto.request.CourseRegisterRequestDTO;
import com.example.courseManagementProject.dto.response.CourseRegisterResponseDTO;
import com.example.courseManagementProject.dto.response.FetchAllCoursesResponseDTO;
import com.example.courseManagementProject.service.interfaces.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
@Tag(name = "Course-Related APIs")
public class CourseController {
    private final CourseService courseService;

    @Operation(
            summary = "Registers a course",
            description = "Accepts the details of the course, like courseName, courseDescription. Only ADMIN can register courses."
    )
    @PostMapping
    public CourseRegisterResponseDTO registerCourse(@RequestBody @Valid CourseRegisterRequestDTO request) {
        return courseService.registerCourse(request);
    }

    @Operation(
            summary = "Fetches all registered courses",
            description = "Fetches all the available courses. This API is accessible to everyone(ADMIN, MANAGER, EMPLOYEE, INTERN)"
    )
    @GetMapping
    public FetchAllCoursesResponseDTO fetchAllCourses() {
        return courseService.fetchAllCourses();
    }
}
