package com.angebhd.studentManagement.repository;

import java.time.Year;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.Semester;


public interface SemesterRepository extends JpaRepository<Semester, UUID>{

    boolean existsByYearAndName(Year year, String name);

    
}