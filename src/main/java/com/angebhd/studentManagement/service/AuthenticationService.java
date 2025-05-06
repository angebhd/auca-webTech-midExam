package com.angebhd.studentManagement.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.angebhd.studentManagement.DTO.LoginRequest;
import com.angebhd.studentManagement.DTO.LoginResponse;
import com.angebhd.studentManagement.DTO.UserData;
import com.angebhd.studentManagement.model.Staff;
import com.angebhd.studentManagement.model.Student;
import com.angebhd.studentManagement.model.Teacher;
import com.angebhd.studentManagement.repository.StaffRepository;
import com.angebhd.studentManagement.repository.StudentRepository;
import com.angebhd.studentManagement.repository.TeacherRepository;
import com.angebhd.studentManagement.service.OtpService.OtpValidationResponse;
import com.angebhd.studentManagement.service.OtpService.OtpValidationResult;

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
    private OtpService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JWTUtilities jwtUtilities;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

    public LoginResponse login(LoginRequest req) {
        System.out.println(req.getLoginAs());
        System.out.println(req.getLoginAs().isEmpty());

        // if (req.getLoginAs().isEmpty()) {

            if (isInteger(req.getUsername())) {
                Optional<Student> st = studentRepository.findById(Integer.parseInt(req.getUsername()));
                if (st.isPresent()) {
                    Student student = st.get();
                    if (encoder.matches(req.getPassword(), student.getPassword())) {
                        String token = jwtUtilities.generateToken(student.getEmail());
                        emailService.adminLoginMailAlert(new UserData(student));
                        UUID otpUUID = UUID.randomUUID();
                        otpService.generateAndSendOtp(otpUUID, String.valueOf(student.getId()), "STUDENT",
                                new UserData(student));
                        return new LoginResponse(true, otpUUID.toString(), "STUDENT",
                                "Successfully logged in as a student",
                                semesterService.getCurrentSemester(), student);
                    } else {
                        return new LoginResponse(false, "Incorect credentials");
                    }

                }
            } else {
                Optional<Student> st = studentRepository.findByEmail(req.getUsername());
                if (st.isPresent()) {
                    Student student = st.get();
                    if (encoder.matches(req.getPassword(), student.getPassword())) {
                        String token = jwtUtilities.generateToken(student.getEmail());

                        // email test
                        emailService.adminLoginMailAlert(new UserData(student));
                        ///

                        LoginResponse resp = new LoginResponse(true, token, "STUDENT",
                                "Successfully logged in as a student", semesterService.getCurrentSemester(), student);
                        // resp.setUserInfo(student);

                        return resp;
                    } else {
                        return new LoginResponse(false, "Incorect credentials");
                    }

                }
            }
        // }else if(req.getLoginAs().equals(req)){        }

        /* STaff */

        Optional<Staff> st = staffRepository.findByEmail(req.getUsername());
        if (st.isPresent()) {
            Staff staff = st.get();
            if (encoder.matches(req.getPassword(), staff.getPassword())) {
                String token = jwtUtilities.generateToken(staff.getEmail());

                // email test
                emailService.adminLoginMailAlert(new UserData(staff));
                ///

                return new LoginResponse(true, token, staff.getRole().toString(),
                        "Successfully logged in as " + staff.getRole().toString(), semesterService.getCurrentSemester(),
                        staff);
            } else {
                return new LoginResponse(false, "Incorect credentials");
            }

        }

        Optional<Teacher> t = teacherRepository.findByEmail(req.getUsername());
        if (t.isPresent()) {
            Teacher teacher = t.get();
            if (encoder.matches(req.getPassword(), teacher.getPassword())) {
                String token = jwtUtilities.generateToken(teacher.getEmail());

                // email test
                emailService.adminLoginMailAlert(new UserData(teacher));
                ///

                LoginResponse resp = new LoginResponse(true, token, teacher.getRole().toString(),
                        "Successfully logged in as" + teacher.getRole().toString(),
                        semesterService.getCurrentSemester(), teacher);
                // resp.setUserInfo(teacher);
                return resp;
            } else {
                return new LoginResponse(false, "Incorect credentials");
            }

        }

        return new LoginResponse(false, "Incorect credentials");

    }

    public LoginResponse validateOTP(UUID otpId, String otp) {
        OtpValidationResponse<UserData> response = otpService.validateOtp(otpId, otp);
        if (response.getResult().equals(OtpValidationResult.SUCCESS)) {

            String token = jwtUtilities.generateToken(response.getData().getEmail());
            return new LoginResponse(true, token,
                    response.getUserRole(), "Successfully logged in as " + response.getUserRole(),
                    semesterService.getCurrentSemester(), response.getData());
        }

        return new LoginResponse(false, response.getResult().toString());
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
