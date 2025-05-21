package com.angebhd.studentManagement.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.angebhd.studentManagement.model.Semester;
import com.angebhd.studentManagement.model.Student;
import com.angebhd.studentManagement.model.StudentRegistration;
import java.util.List;
import com.angebhd.studentManagement.model.OfferedCourse;


public interface StudentRegistrationRepository extends JpaRepository<StudentRegistration, UUID> {

    Optional<StudentRegistration> findByStudentAndSemester(Student student, Semester semester);

    List<StudentRegistration> findByCourses(List<OfferedCourse> courses);

    List<StudentRegistration> findByStudent(Student student);
}