package com.example.courseManagementProject.repository;

import com.example.courseManagementProject.entity.CourseStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseStatusRepository extends JpaRepository<CourseStatusEntity, Integer> {
    @Query("SELECT l FROM CourseStatusEntity l WHERE l.course.id = :courseId AND l.user.id = :userId")
    List<CourseStatusEntity> findByCourseAndUser(Integer courseId, Integer userId);
}
