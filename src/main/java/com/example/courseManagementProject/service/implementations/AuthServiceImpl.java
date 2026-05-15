package com.example.courseManagementProject.service.implementations;

import com.example.courseManagementProject.dto.request.LoginRequestDTO;
import com.example.courseManagementProject.dto.request.RegisterRequestDTO;
import com.example.courseManagementProject.dto.response.LoginResponseDTO;
import com.example.courseManagementProject.dto.response.RegisterResponseDTO;
import com.example.courseManagementProject.entity.User;
import com.example.courseManagementProject.enums.Department;
import com.example.courseManagementProject.enums.EmployeeStatus;
import com.example.courseManagementProject.enums.Role;
import com.example.courseManagementProject.exception.EntityNotFoundException;
import com.example.courseManagementProject.exception.InvalidArgumentException;
import com.example.courseManagementProject.repository.UserRepository;
import com.example.courseManagementProject.service.interfaces.AuthService;
import com.example.courseManagementProject.service.interfaces.RefreshTokenService;
import com.example.courseManagementProject.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public RegisterResponseDTO registerUser(RegisterRequestDTO request) {
        if(!isRoleValid(request.getRole())) {
            throw new InvalidArgumentException("Role specified is not valid. Please enter one of the options: [INTERN, EMPLOYEE, MANAGER, ADMIN]");
        }
        else if(!isDepartmentValid(request.getDepartment())) {
            throw new InvalidArgumentException("Department specified is not valid. Please enter one of the options: [DELIVERY, HR, MANAGEMENT]");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .department(Department.valueOf(request.getDepartment()))
                .employeeStatus(EmployeeStatus.OFF_BENCH)
                .build();

        userRepository.save(user);

        return RegisterResponseDTO.builder()
                .userId(user.getId())
                .userRole(user.getRole().toString())
                .message("User Registered successfully!")
                .build();
    }

    @Override
    public LoginResponseDTO loginUser(LoginRequestDTO request) {
        LoginResponseDTO response = null;

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            User existingUser = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("Specified user does not exist"));

            String accessToken = jwtUtil.generateToken(existingUser.getUsername(), existingUser.getRole().toString());
            String refreshToken = refreshTokenService.generateRefreshToken(existingUser);

            response = LoginResponseDTO.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .message("User is successfully logged in. Can use accessToken for next 10min to interact with the APIs. RefreshToken has the validity of one day.")
                    .build();
        } catch(Exception exception) {
            log.error("Exception has occurred: {}", exception.getLocalizedMessage());
        }

        return response;
    }

    public boolean isDepartmentValid(String departmentSpecified) {
        Department[] validDepartments = Department.values();
        boolean departmentFlag = false;
        for(Department department : validDepartments) {
            if(departmentSpecified.equalsIgnoreCase(department.toString())) {
                departmentFlag = true;
                break;
            }
        }

        return departmentFlag;
    }

    public boolean isRoleValid(String roleSpecified) {
        Role[] validRoles = Role.values();
        boolean roleFlag = false;
        for(Role role : validRoles) {
            if(roleSpecified.equalsIgnoreCase(role.toString())) {
                roleFlag = true;
                break;
            }
        }

        return roleFlag;
    }
}
