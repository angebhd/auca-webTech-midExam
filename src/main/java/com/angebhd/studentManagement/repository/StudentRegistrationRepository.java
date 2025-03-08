package com.angebhd.studentManagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.StudentRegistration;

public interface StudentRegistrationRepository extends JpaRepository<StudentRegistration, UUID> {

    
}