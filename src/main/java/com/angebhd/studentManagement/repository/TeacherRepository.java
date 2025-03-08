package com.angebhd.studentManagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, UUID> {
    
}