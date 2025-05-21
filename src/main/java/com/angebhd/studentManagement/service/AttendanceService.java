package com.angebhd.studentManagement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angebhd.studentManagement.DTO.AttendaceFormat;
import com.angebhd.studentManagement.DTO.OperationResult;
import com.angebhd.studentManagement.model.Attendance;
import com.angebhd.studentManagement.model.OfferedCourse;
import com.angebhd.studentManagement.model.Student;
import com.angebhd.studentManagement.repository.AttendaceRepository;
import com.angebhd.studentManagement.repository.OfferedCourseRepository;
import com.angebhd.studentManagement.repository.StudentRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendaceRepository attendaceRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private OfferedCourseRepository offeredCourseRepository;

    public OperationResult addList(List<AttendaceFormat> attList, UUID course) {
        Optional<OfferedCourse> oc = offeredCourseRepository.findById(course);
        if (oc.isPresent()) {

            OfferedCourse offeredCourse = oc.get();
            List<Attendance> attendances = new ArrayList<Attendance>();
            for (AttendaceFormat a : attList) {

                Optional<Student> st = studentRepository.findById(a.getId());
                if (st.isEmpty()) {
                    continue;
                }
                Attendance attendance = new Attendance();
                attendance.setStudent(st.get());
                attendance.setCourse(offeredCourse);
                attendance.setDate(LocalDate.now());
                attendance.setStatus(a.getStatus());
                attendances.add(attendance);
            }
            attendaceRepository.saveAll(attendances);
            if (attList.size() != attendances.size()) {
                int b = attList.size() - attendances.size();
                return new OperationResult(true, "Attendaces saved but " + b + " attendance(s) have not been savedd");

            }
            return new OperationResult(true, "Attendaces saved");

        } else {

            return new OperationResult(false, "Course not found");
        }

    }

}