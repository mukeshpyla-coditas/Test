package com.example.courseManagementProject.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDTO {
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Size(min = 6, max = 8)
    private String password;
}
