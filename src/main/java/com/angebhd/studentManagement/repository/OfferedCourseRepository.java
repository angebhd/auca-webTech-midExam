package com.angebhd.studentManagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.Course;
import com.angebhd.studentManagement.model.OfferedCourse;
import com.angebhd.studentManagement.model.Semester;
import com.angebhd.studentManagement.model.Teacher;

import java.util.List;


public interface OfferedCourseRepository extends JpaRepository<OfferedCourse, UUID> {

    boolean existsBySemesterAndCourseAndGroup(Semester semester, Course course, char group);

    List<OfferedCourse> findByTeacher(Teacher teacher);

    
}