package com.angebhd.studentManagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.Fees;
import com.angebhd.studentManagement.model.Student;
import com.angebhd.studentManagement.model.StudentRegistration;

public interface FeesRepository extends JpaRepository<Fees, UUID>{

    Optional<Fees> findByStudentAndRegistration(Student student, StudentRegistration registration);
    
}