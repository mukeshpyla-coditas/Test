package com.example.courseManagementProject.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CertificateResponseDTO {
    private Integer candidateId;
    private String candidateName;
    private String courseName;
    private String message;
}
