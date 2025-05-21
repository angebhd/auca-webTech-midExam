package com.angebhd.studentManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angebhd.studentManagement.DTO.GlobalSearchObject;
import com.angebhd.studentManagement.model.Student;
import com.angebhd.studentManagement.repository.AcademicUnitRepository;
import com.angebhd.studentManagement.repository.AttendaceRepository;
import com.angebhd.studentManagement.repository.CourseRepository;
import com.angebhd.studentManagement.repository.FeesRepository;
import com.angebhd.studentManagement.repository.GradesRepository;
import com.angebhd.studentManagement.repository.OfferedCourseRepository;
import com.angebhd.studentManagement.repository.PaymentRepository;
import com.angebhd.studentManagement.repository.SemesterRepository;
import com.angebhd.studentManagement.repository.StaffRepository;
import com.angebhd.studentManagement.repository.StudentRegistrationRepository;
import com.angebhd.studentManagement.repository.StudentRepository;
import com.angebhd.studentManagement.repository.TeacherRepository;

@Service
public class GlobalSearchService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private OfferedCourseRepository offeredCourseRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private AcademicUnitRepository academicUnitRepository;

    
    @Autowired
    private FeesRepository feesRepository;

    @Autowired
    private AttendaceRepository attendaceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentRegistrationRepository studentRegistrationRepository;


    public GlobalSearchObject adminSearch() {
        GlobalSearchObject globalSearchObject = new GlobalSearchObject();
        globalSearchObject.setStudents(studentRepository.findAll());
        globalSearchObject.setTeachers(teacherRepository.findAll());
        globalSearchObject.setStaffs(staffRepository.findAll());

        globalSearchObject.setOfferedCourses(offeredCourseRepository.findAll());
        globalSearchObject.setCourses(courseRepository.findAll());

        globalSearchObject.setAcademicUnits(academicUnitRepository.findAll());
        globalSearchObject.setSemesters(semesterRepository.findAll());
        globalSearchObject.setFees(feesRepository.findAll());
        globalSearchObject.setAttendances(attendaceRepository.findAll());
        globalSearchObject.setPayments(paymentRepository.findAll());
        globalSearchObject.setRegistrations(studentRegistrationRepository.findAll());
        return globalSearchObject;
    }

    public GlobalSearchObject studentSearch(String id) {
        Optional<Student> st = studentRepository.findById(Integer.parseInt(id));
        if(st.isEmpty()) return null;

        return new GlobalSearchObject(studentRegistrationRepository.findByStudent(st.get()),courseRepository.findAll() ,feesRepository.findAll(), paymentRepository.findAll());

    }


}