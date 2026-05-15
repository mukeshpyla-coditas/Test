package com.example.courseManagementProject.service.interfaces;

import com.example.courseManagementProject.dto.request.LectureRegisterRequestDTO;
import com.example.courseManagementProject.dto.response.LectureRegisterResponseDTO;

public interface LectureService {
    LectureRegisterResponseDTO registerLecture(LectureRegisterRequestDTO request);
}
