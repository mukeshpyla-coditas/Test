package com.example.courseManagementProject.global;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class GlobalResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
}
