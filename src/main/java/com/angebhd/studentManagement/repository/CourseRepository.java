package com.angebhd.studentManagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.Course;

public interface CourseRepository extends JpaRepository<Course, UUID>{

    boolean existsByCode(String code);
    
}