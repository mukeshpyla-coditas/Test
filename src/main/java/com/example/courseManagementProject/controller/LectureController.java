package com.example.courseManagementProject.controller;

import com.example.courseManagementProject.dto.request.LectureRegisterRequestDTO;
import com.example.courseManagementProject.dto.response.LectureRegisterResponseDTO;
import com.example.courseManagementProject.service.interfaces.LectureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lecture")
@RequiredArgsConstructor
@Tag(name = "Lecture-related APIs")
public class LectureController {
    private final LectureService lectureService;

    @Operation(
            summary = "Registers a lecture into a course",
            description = "Accepts the details of the lecture like, lectureName, lectureDescription. " +
                    "NOTE that you can only register a lecture into an existing course. And, only ADMIN has the authority to add lectures into course."
    )
    @PostMapping
    public LectureRegisterResponseDTO registerLecture(@RequestBody @Valid LectureRegisterRequestDTO request) {
        return lectureService.registerLecture(request);
    }
}
