package com.angebhd.studentManagement.model;



import com.angebhd.studentManagement.model.enumeration.ETeacherQualifications;
import com.angebhd.studentManagement.model.enumeration.ETeacherRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {

    @Id
    private UUID id;
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;


    @Column(name = "qualification")
    @Enumerated(EnumType.STRING)
    private ETeacherQualifications qualification;

    @Column(name = "role")
    private ETeacherRole role;


    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;


    /*  */

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<OfferedCourse> courses;
}