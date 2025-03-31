package com.angebhd.studentManagement.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angebhd.studentManagement.DTO.OperationResult;
import com.angebhd.studentManagement.model.OfferedCourse;
import com.angebhd.studentManagement.model.Teacher;
import com.angebhd.studentManagement.repository.OfferedCourseRepository;
import com.angebhd.studentManagement.repository.TeacherRepository;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private OfferedCourseRepository offeredCourseRepository;

    public OperationResult add (Teacher teacher){

        teacherRepository.save(teacher);
        return new OperationResult(true, "Teacher "+teacher.getFirstName() +" " + teacher.getLastName()+" added successfully");
    }

    public List<Teacher> get(){
        return teacherRepository.findAll();
    }

    public OperationResult update (Teacher teacher){
        Optional<Teacher> t = teacherRepository.findById(teacher.getId());

        if (t.isPresent()) {
            Teacher newTeacher = t.get();
            newTeacher.setAddress(teacher.getAddress());
            newTeacher.setEmail(teacher.getEmail());
            newTeacher.setFirstName(teacher.getFirstName());
            newTeacher.setLastName(teacher.getLastName());
            newTeacher.setPhone(teacher.getPhone());
            newTeacher.setQualification(teacher.getQualification());
            newTeacher.setRole(teacher.getRole());

            teacherRepository.save(newTeacher);
            return new OperationResult(true, "Teacher "+teacher.getFirstName() +" " + teacher.getLastName()+" updated successfully");
        }
        return new OperationResult(false, "Teacher not found");
    }
    
    public OperationResult delete(Teacher teacher){
        Optional<Teacher> t = teacherRepository.findById(teacher.getId());
        if (t.isPresent()) {
            teacherRepository.deleteById(teacher.getId());
            return new OperationResult(true, "Teacher "+teacher.getFirstName() +" " + teacher.getLastName()+" deleted successfully") ;  
        }
        return new OperationResult(false, "Teacher not found");
    }

    public List<OfferedCourse> getCourses(UUID teacherId){
        Optional<Teacher> t = teacherRepository.findById(teacherId);

        if (t.isPresent()) {
            Teacher teacher = t.get();
            List<OfferedCourse> courses = offeredCourseRepository.findByTeacher(teacher);

            for (OfferedCourse course : courses) {
                course.setTeacher(null);
                
                
            }


            return courses;
        } 
        return null;
    }
}