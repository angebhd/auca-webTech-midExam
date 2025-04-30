package com.angebhd.studentManagement.DTO;

import com.angebhd.studentManagement.model.Staff;
import com.angebhd.studentManagement.model.Student;
import com.angebhd.studentManagement.model.Teacher;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserData {

    private String firstName;
    private String lastName;
    private String id;
    private String faculty;
    private String department;
    private String program;
    private String role;
    private String qualification;

    public UserData(Student st) {

        this.setFirstName(st.getFirstName());
        this.setLastName(st.getLastName());
        this.setId(""+ st.getId());

        if (st.getDepartment() != null) {
            this.setFaculty(st.getDepartment().getParent().getName());
            this.setDepartment(st.getDepartment().getName());
        }
        this.setProgram(st.getProgram().toString());
        this.setRole("STUDENT");
    }

    public UserData(Staff st) {

        this.setFirstName(st.getFirstName());
        this.setLastName(st.getLastName());
        this.setId(st.getEmail());


        this.setRole(st.getRole().toString());
    }

    public UserData(Teacher st) {

        this.setFirstName(st.getFirstName());
        this.setLastName(st.getLastName());
        this.setId(st.getEmail());
        this.setQualification(st.getQualification().toString());

        this.setRole(st.getRole().toString());
    }

}