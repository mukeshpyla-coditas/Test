package com.example.courseManagementProject.controller;

import com.example.courseManagementProject.dto.request.AccessCourseRequestDTO;
import com.example.courseManagementProject.dto.request.EnrollmentRequestDTO;
import com.example.courseManagementProject.dto.response.AccessCourseResponseDTO;
import com.example.courseManagementProject.dto.response.EnrollmentResponseDTO;
import com.example.courseManagementProject.dto.response.FetchAllCoursesResponseDTO;
import com.example.courseManagementProject.dto.response.GetProgressOfCourseResponseDTO;
import com.example.courseManagementProject.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User-related APIs")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Enrolls user into a course",
            description = "Accepts the details of user and course that the user wants to enroll in, and enrolls the user, " +
                    "if the user is registered and valid."
    )
    @PostMapping("/enrollment")
    public EnrollmentResponseDTO enrollUserToCourse(@RequestBody @Valid EnrollmentRequestDTO request) {
        return userService.enrollUserToCourse(request);
    }

    @Operation(
            summary = "Allows user to access an enrolled course",
            description = "Accepts the details of course and lecture that the user wants to access. " +
                    "Allows access to the lecture if the user is registered and enrolled into the course."
    )
    @PostMapping("/access-course")
    public AccessCourseResponseDTO accessCourse(@RequestBody @Valid AccessCourseRequestDTO request) {
        return userService.accessEnrolledCourse(request);
    }

    @Operation(
            summary = "Fetched all enrolled courses of the user.",
            description = "Will Authenticate the user based on the AccessToken that he uses to access the API. " +
                    "If the user is valid, will fetch all the enrolled courses of the user."
    )
    @GetMapping("/enrolled-courses")
    public FetchAllCoursesResponseDTO fetchEnrolledCourses() {
        return userService.fetchEnrolledCourses();
    }

    @Operation(
            summary = "Calculates the completion progress for specified course.",
            description = "Accepts the courseId as the input parameter(as a path variable) and fetches the progress of the user for that course. " +
                    "NOTE that user must first be enrolled into the course before accessing this API."
    )
    @GetMapping("/progress/{courseId}")
    public GetProgressOfCourseResponseDTO getProgress(@Parameter(description = "ID of course user wants to check progress of.") @PathVariable(name = "courseId") Integer courseId) {
        return userService.getProgress(courseId);
    }
}
