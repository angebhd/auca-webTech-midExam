package com.angebhd.studentManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.Student;



public interface StudentRepository extends JpaRepository<Student, Integer>{

    Optional<Student> findByEmail(String email);
}