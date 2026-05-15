package com.example.courseManagementProject.repository;

import com.example.courseManagementProject.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    RefreshToken findByRefreshToken(String refreshToken);
}
