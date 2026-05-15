package com.example.courseManagementProject.service.interfaces;

import com.example.courseManagementProject.dto.response.CertificateResponseDTO;
import com.example.courseManagementProject.dto.response.TestAttemptResponseDTO;

public interface TestService {
    TestAttemptResponseDTO attemptTest(Integer courseId);
    CertificateResponseDTO getCertificate(Integer courseId);
}
