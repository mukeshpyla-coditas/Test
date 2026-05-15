package com.example.courseManagementProject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private Integer statusCode;
    private String message;
    private LocalDate timestamp;
}
