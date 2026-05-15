package com.example.courseManagementProject.service.implementations;

import com.example.courseManagementProject.dto.response.CertificateResponseDTO;
import com.example.courseManagementProject.dto.response.TestAttemptResponseDTO;
import com.example.courseManagementProject.entity.*;
import com.example.courseManagementProject.enums.TestStatus;
import com.example.courseManagementProject.exception.EnrollmentException;
import com.example.courseManagementProject.exception.EntityNotFoundException;
import com.example.courseManagementProject.exception.IncompleteCourseException;
import com.example.courseManagementProject.exception.TestNotPassedException;
import com.example.courseManagementProject.repository.*;
import com.example.courseManagementProject.service.interfaces.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestServiceImpl implements TestService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final UserServiceImpl userService;
    private final TestRepository testRepository;
    private final CertificateRepository certificateRepository;
    private final TestStatusRepository testStatusRepository;

    @Override
    public TestAttemptResponseDTO attemptTest(Integer courseId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Specified user is not found"));
        Course requestedCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Specified course is not found"));

        if(!userService.validateEnrollmentCourse(existingUser, requestedCourse)) {
            throw new EnrollmentException("Please enroll into the course before trying to access the test module.");
        }

        Integer totalLecturesPresent = requestedCourse.getLectureList().size();
        Integer completedLecturesCount = userService.getCompletedLecturesCount(requestedCourse, existingUser);
        Test existingTest = null;

        if(Objects.equals(totalLecturesPresent, completedLecturesCount)) {
            log.info("User " + username + " has successfully attempted the test and is PASSED!");
            existingTest = requestedCourse.getTest();
            existingTest.setTestStatus(TestStatus.PASSED);
            testRepository.save(existingTest);
        }
        else throw new IncompleteCourseException("Please all the lectures in the course to access the test module");

        TestStatusEntity testStatus = TestStatusEntity.builder()
                .user(existingUser)
                .course(requestedCourse)
                .test(existingTest)
                .testStatus(TestStatus.PASSED)
                .build();

        testStatusRepository.save(testStatus);

        return TestAttemptResponseDTO.builder()
                .courseName(requestedCourse.getCourseName())
                .testStatus(existingTest.getTestStatus().toString())
                .message("You have successfully passed the test. You can now download your certificate.")
                .build();
    }

    @Override
    public CertificateResponseDTO getCertificate(Integer courseId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Specified user is not found"));
        Course requestedCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Specified course is not found"));

        if(!userService.validateEnrollmentCourse(existingUser, requestedCourse)) {
            throw new EnrollmentException("Please enroll into the course before trying to access the test module.");
        }

        TestStatusEntity testStatus = testStatusRepository.findByUser(existingUser)
                .orElseThrow(() -> new TestNotPassedException("You must pass the test before getting your course completion certificate."));

        if("PASSED".equals(testStatus.getTestStatus().toString())) {
            Certificate certificate = Certificate.builder()
                    .candidate(existingUser)
                    .candidateRole(existingUser.getRole())
                    .completedCourseName(requestedCourse.getCourseName())
                    .completedCourseId(requestedCourse.getId())
                    .build();
            certificateRepository.save(certificate);

            return CertificateResponseDTO.builder()
                    .candidateId(existingUser.getId())
                    .candidateName(existingUser.getUsername())
                    .courseName(requestedCourse.getCourseName())
                    .message("Candidate has successfully completed the course and is certified!")
                    .build();
        }
        else {
            throw new TestNotPassedException("There is not entry of test attempt. Please attempt the test before accessing the certificate.");
        }
    }
}
