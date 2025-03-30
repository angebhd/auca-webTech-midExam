package com.angebhd.studentManagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.Grades;
import com.angebhd.studentManagement.model.Student;

import java.util.List;


public interface GradesRepository extends JpaRepository<Grades, UUID>{

    List<Grades> findByStudent(Student student);
}