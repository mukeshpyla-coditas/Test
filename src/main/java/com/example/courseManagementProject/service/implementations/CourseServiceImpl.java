package com.example.courseManagementProject.service.implementations;

import com.example.courseManagementProject.dto.request.CourseRegisterRequestDTO;
import com.example.courseManagementProject.dto.response.CourseRegisterResponseDTO;
import com.example.courseManagementProject.dto.response.FetchAllCoursesResponseDTO;
import com.example.courseManagementProject.entity.Course;
import com.example.courseManagementProject.entity.Lecture;
import com.example.courseManagementProject.enums.CourseStatus;
import com.example.courseManagementProject.repository.CourseRepository;
import com.example.courseManagementProject.service.interfaces.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    @Override
    public CourseRegisterResponseDTO registerCourse(CourseRegisterRequestDTO request) {
        Course course = Course.builder()
                .courseName(request.getName())
                .courseDescription(request.getDescription())
                .status(CourseStatus.CREATED)
                .build();

        courseRepository.save(course);

        String adminName = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("Course '{}' is created by: {}", course.getCourseName(), adminName);

        return CourseRegisterResponseDTO.builder()
                .courseName(course.getCourseName())
                .courseId(course.getId())
                .message("Course is successfully registered")
                .build();
    }

    @Override
    public FetchAllCoursesResponseDTO fetchAllCourses() {
        List<Course> existingCourses = courseRepository.findAll();
        Map<String, List<String>> courseList = new HashMap<>();

        for(Course course : existingCourses) {
            List<Lecture> lectureList = course.getLectureList();
            List<String> lectureNames = lectureList.stream().map(Lecture::getLectureName).toList();
            courseList.put(course.getCourseName(), new ArrayList<>(lectureNames));
        }

        return FetchAllCoursesResponseDTO.builder()
                .courseList(courseList)
                .build();
    }
}
