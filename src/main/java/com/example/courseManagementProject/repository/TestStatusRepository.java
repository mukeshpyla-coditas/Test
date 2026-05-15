package com.example.courseManagementProject.repository;

import com.example.courseManagementProject.entity.TestStatusEntity;
import com.example.courseManagementProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestStatusRepository extends JpaRepository<TestStatusEntity, Integer> {
    Optional<TestStatusEntity> findByUser(User user);
}
