package com.example.courseManagementProject.service.implementations;

import com.example.courseManagementProject.dto.request.LectureRegisterRequestDTO;
import com.example.courseManagementProject.dto.response.LectureRegisterResponseDTO;
import com.example.courseManagementProject.entity.Course;
import com.example.courseManagementProject.entity.Lecture;
import com.example.courseManagementProject.enums.LectureStatus;
import com.example.courseManagementProject.exception.EntityNotFoundException;
import com.example.courseManagementProject.repository.CourseRepository;
import com.example.courseManagementProject.repository.LectureRepository;
import com.example.courseManagementProject.service.interfaces.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LectureServiceImpl implements LectureService {
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;

    @Override
    public LectureRegisterResponseDTO registerLecture(LectureRegisterRequestDTO request) {
        Course existingCourse = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Specified course does not exist."));

        Lecture lecture = Lecture.builder()
                .lectureName(request.getLectureName())
                .lectureDescription(request.getLectureDescription())
                .course(existingCourse)
                .build();

        lectureRepository.save(lecture);

        existingCourse.getLectureList().add(lecture);

        return LectureRegisterResponseDTO.builder()
                .courseName(existingCourse.getCourseName())
                .lectureId(lecture.getId())
                .lectureName(lecture.getLectureName())
                .build();
    }
}
