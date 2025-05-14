package com.angebhd.studentManagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.angebhd.studentManagement.model.Staff;
import com.angebhd.studentManagement.model.StaffPrincipal;
import com.angebhd.studentManagement.model.Student;
import com.angebhd.studentManagement.model.StudentPrincipal;
import com.angebhd.studentManagement.model.Teacher;
import com.angebhd.studentManagement.model.TeacherPrincipal;
import com.angebhd.studentManagement.repository.StaffRepository;
import com.angebhd.studentManagement.repository.StudentRepository;
import com.angebhd.studentManagement.repository.TeacherRepository;

@Service
public class UsersDetailsService implements UserDetailsService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    public UserDetails loadUserByUsername(String email) {

        Optional<Student> student = studentRepository.findByEmail(email);
        
        if(student.isPresent()){
            System.out.println("Load student");
            return new StudentPrincipal(student.get());
        }
        
        Optional<Teacher> teacher = teacherRepository.findByEmail(email);
        
        if (teacher.isPresent()) {
            System.out.println("Load teacher");
            return new TeacherPrincipal(teacher.get());
        }
        
        Optional<Staff> staff = staffRepository.findByEmail(email);
        if (staff.isPresent()) {
            System.out.println("Load staff");
            return new StaffPrincipal(staff.get());
        }

        throw new UsernameNotFoundException("Email not found");

    }

}