package com.example.courseManagementProject.service.implementations;

import com.example.courseManagementProject.dto.request.AccessCourseRequestDTO;
import com.example.courseManagementProject.dto.request.EnrollmentRequestDTO;
import com.example.courseManagementProject.dto.response.AccessCourseResponseDTO;
import com.example.courseManagementProject.dto.response.EnrollmentResponseDTO;
import com.example.courseManagementProject.dto.response.FetchAllCoursesResponseDTO;
import com.example.courseManagementProject.dto.response.GetProgressOfCourseResponseDTO;
import com.example.courseManagementProject.entity.*;
import com.example.courseManagementProject.enums.CourseStatus;
import com.example.courseManagementProject.enums.LectureStatus;
import com.example.courseManagementProject.enums.TestStatus;
import com.example.courseManagementProject.exception.EnrollmentException;
import com.example.courseManagementProject.exception.EntityNotFoundException;
import com.example.courseManagementProject.repository.*;
import com.example.courseManagementProject.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final CourseRepository courseRepository;
    private final LectureRepository lectureRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseStatusRepository courseStatusRepository;

    @Override
    public EnrollmentResponseDTO enrollUserToCourse(EnrollmentRequestDTO request) {
        User existingUser = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Specified user is not found"));

        Course existingCourse = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Specified course is not found"));

        Enrollment enrollment = Enrollment.builder()
                .user(existingUser)
                .course(existingCourse)
                .build();

        enrollmentRepository.save(enrollment);

        existingUser.getEnrollmentList().add(enrollment);
        existingCourse.getEnrollmentList().add(enrollment);

        return EnrollmentResponseDTO.builder()
                .userId(existingUser.getId())
                .username(existingUser.getUsername())
                .courseName(existingCourse.getCourseName())
                .message("User is successfully enrolled into the course.")
                .build();
    }

    @Override
    public FetchAllCoursesResponseDTO fetchEnrolledCourses() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Specified user is not found"));

        Map<String, List<String>> enrolledCoursesList = new HashMap<>();

        List<Enrollment> enrollmentList = existingUser.getEnrollmentList();
        for(Enrollment enrollment : enrollmentList) {
            Course enrolledCourse = enrollment.getCourse();
            List<Lecture> lectureList = enrolledCourse.getLectureList();
            List<String> lectureNames = lectureList.stream().map(Lecture::getLectureName).toList();
            enrolledCoursesList.put(enrolledCourse.getCourseName(), new ArrayList<>(lectureNames));
        }

        return FetchAllCoursesResponseDTO.builder()
                .courseList(enrolledCoursesList)
                .build();
    }

    @Override
    public AccessCourseResponseDTO accessEnrolledCourse(AccessCourseRequestDTO request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Specified user is not found"));

        Course requestedCourse = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Specified course is not found"));
        Lecture requestedLecture = lectureRepository.findById(request.getLectureId())
                .orElseThrow(() -> new EntityNotFoundException("Specified lecture is not present in the requested course."));

        if(!validateEnrollmentCourse(existingUser, requestedCourse)) {
            throw new EnrollmentException("Please enroll into course before accessing it.");
        }

        if(!validateLectureOfCourse(requestedCourse, requestedLecture)) {
            throw new EntityNotFoundException("Specified lecture is not found in the course.");
        }

        CourseStatusEntity courseStatusEntity = CourseStatusEntity.builder()
                .user(existingUser)
                .course(requestedCourse)
                .lecture(requestedLecture)
                .status(LectureStatus.COMPLETED)
                .build();

        courseStatusRepository.save(courseStatusEntity);

        return AccessCourseResponseDTO.builder()
                .courseName(requestedCourse.getCourseName())
                .lectureName(requestedLecture.getLectureName())
                .message("You have completed this lecture. May continue to the next lecture.")
                .build();
    }

    @Override
    public GetProgressOfCourseResponseDTO getProgress(Integer courseId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Specified user is not found"));
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Specified course does not exist"));

        Integer totalLectureCount = existingCourse.getLectureList().size();
        Integer completedLecturesCount = getCompletedLecturesCount(existingCourse, existingUser);

        if(Objects.equals(totalLectureCount, completedLecturesCount)) {
            Test test = new Test();
            test.setCourse(existingCourse);
            test.setTestStatus(TestStatus.ACTIVE);
            testRepository.save(test);

            existingCourse.setStatus(CourseStatus.COMPLETED);
            existingCourse.setTest(test);
            courseRepository.save(existingCourse);
        }

        Double totalProgress = (completedLecturesCount.doubleValue() / totalLectureCount.doubleValue()) * 100.0;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        return GetProgressOfCourseResponseDTO.builder()
                .username(existingUser.getUsername())
                .courseName(existingCourse.getCourseName())
                .percentageCompleted(Double.valueOf(decimalFormat.format(totalProgress)))
                .build();
    }

    public Integer getCompletedLecturesCount(Course existingCourse, User existingUser) {
        List<CourseStatusEntity> courseStatusEntityList = courseStatusRepository.findByCourseAndUser(existingCourse.getId(), existingUser.getId());
        Integer completedLecturesCount = 0;

        for(CourseStatusEntity courseStatus : courseStatusEntityList) {
            if("COMPLETED".equals(courseStatus.getStatus().toString())) completedLecturesCount++;
        }

        return completedLecturesCount;
    }

    public boolean validateEnrollmentCourse(User existingUser, Course requestedCourse) {
        List<Enrollment> enrollmentList = existingUser.getEnrollmentList();
        boolean courseFlag = false;

        for(Enrollment enrollment : enrollmentList) {
            Course enrolledCourse = enrollment.getCourse();
            if(enrolledCourse.equals(requestedCourse)) {
                courseFlag = true;
                break;
            }
        }

        return courseFlag;
    }

    public boolean validateLectureOfCourse(Course requestedCourse, Lecture requestedLecture) {
        List<Lecture> lectureList = requestedCourse.getLectureList();
        boolean lectureFlag = false;

        for(Lecture lecture : lectureList) {
            if(lecture.equals(requestedLecture)) {
                lectureFlag = true;
                break;
            }
        }

        return lectureFlag;
    }
}
