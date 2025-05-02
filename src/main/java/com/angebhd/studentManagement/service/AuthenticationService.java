package com.angebhd.studentManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.angebhd.studentManagement.DTO.LoginRequest;
import com.angebhd.studentManagement.DTO.LoginResponse;
import com.angebhd.studentManagement.model.Staff;
import com.angebhd.studentManagement.model.Student;
import com.angebhd.studentManagement.model.Teacher;
import com.angebhd.studentManagement.repository.StaffRepository;
import com.angebhd.studentManagement.repository.StudentRepository;
import com.angebhd.studentManagement.repository.TeacherRepository;

@Service
public class AuthenticationService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private JWTUtilities jwtUtilities;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

    public LoginResponse login(LoginRequest req) {

        /* For students */
        if (isInteger(req.getUsername())) {
            Optional<Student> st = studentRepository.findById(Integer.parseInt(req.getUsername()));
            if (st.isPresent()) {
                Student student = st.get();
                if (encoder.matches(req.getPassword(), student.getPassword())) {
                    String token = jwtUtilities.generateToken(student.getEmail());
                    return new LoginResponse(true, token, "STUDENT", "Successfully logged in as a student", semesterService.getCurrentSemester(), student);
                } else {
                    return new LoginResponse(false,  "Incorect credentials");
                }

            }
        } else {
            Optional<Student> st = studentRepository.findByEmail(req.getUsername());
            if (st.isPresent()) {
                Student student = st.get();
                if (encoder.matches(req.getPassword(), student.getPassword())) {
                    String token = jwtUtilities.generateToken(student.getEmail());
                    LoginResponse resp = new LoginResponse(true, token, "STUDENT", "Successfully logged in as a student",  semesterService.getCurrentSemester(), student);
                    // resp.setUserInfo(student);
                    
                    return resp;
                } else {
                    return new LoginResponse(false,  "Incorect credentials");
                }

            }
        }

        /* STaff */

        Optional<Staff> st = staffRepository.findByEmail(req.getUsername());
        if (st.isPresent()) {
            Staff staff = st.get();
            if (encoder.matches(req.getPassword(), staff.getPassword())) {
                String token = jwtUtilities.generateToken(staff.getEmail());
                return new LoginResponse(true, token, staff.getRole().toString(),
                        "Successfully logged in as " + staff.getRole().toString(), semesterService.getCurrentSemester(), staff);
            } else {
                return new LoginResponse(false,  "Incorect credentials");
            }

        }

        Optional<Teacher> t = teacherRepository.findByEmail(req.getUsername());
        if (t.isPresent()) {
            Teacher teacher = t.get();
            if (encoder.matches(req.getPassword(), teacher.getPassword())) {
                String token = jwtUtilities.generateToken(teacher.getEmail());
                LoginResponse resp = new LoginResponse(true, token, teacher.getRole().toString(), "Successfully logged in as" + teacher.getRole().toString(),  semesterService.getCurrentSemester(), teacher);
                // resp.setUserInfo(teacher);
                return resp;
            } else {
                return new LoginResponse(false,  "Incorect credentials");
            }

        }

        return new LoginResponse(false,  "Incorect credentials");

    }


    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



}
