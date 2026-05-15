package com.example.courseManagementProject.controller;

import com.example.courseManagementProject.dto.response.CertificateResponseDTO;
import com.example.courseManagementProject.dto.response.TestAttemptResponseDTO;
import com.example.courseManagementProject.service.interfaces.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Tag(name = "Test-related APIs")
public class TestController {
    private final TestService testService;

    @Operation(
            summary = "Allows user to attempt the test",
            description = "User can attempt the test via this API. NOTE that, the course must be fully completed before accessing the test."
    )
    @PostMapping("/{courseId}")
    public TestAttemptResponseDTO attemptTest(@PathVariable(name = "courseId") Integer courseId) {
        return testService.attemptTest(courseId);
    }

    @Operation(
            summary = "Allows user to access the certificate",
            description = "User should complete the test and pass in it to access this API and get his course completion certificate."
    )
    @GetMapping("/certificate/{courseId}")
    public CertificateResponseDTO getCertificate(@PathVariable(name = "courseId") Integer courseId) {
        return testService.getCertificate(courseId);
    }
}
