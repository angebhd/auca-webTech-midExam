package com.angebhd.studentManagement.repository;

import java.time.Year;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.Semester;
import com.angebhd.studentManagement.model.enumeration.ESemesterName;
import com.angebhd.studentManagement.model.enumeration.ESemesterStatus;

public interface SemesterRepository extends JpaRepository<Semester, UUID> {

    boolean existsByYearAndName(Year year, ESemesterName name);

    // to find current semester
    Optional<Semester> findFirstByStatusOrderByStartDateDesc(ESemesterStatus status);
    Optional<Semester> findFirstByOrderByStartDateDesc();

}