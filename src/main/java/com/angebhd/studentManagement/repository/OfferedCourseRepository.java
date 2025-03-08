package com.angebhd.studentManagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.OfferedCourse;

public interface OfferedCourseRepository extends JpaRepository<OfferedCourse, UUID> {

    
}