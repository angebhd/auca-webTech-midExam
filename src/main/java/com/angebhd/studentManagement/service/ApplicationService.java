package com.angebhd.studentManagement.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angebhd.studentManagement.DTO.OperationResult;
import com.angebhd.studentManagement.model.AcademicUnit;
import com.angebhd.studentManagement.model.Application;
import com.angebhd.studentManagement.model.Student;
import com.angebhd.studentManagement.model.enumeration.EAcademicUnitType;
import com.angebhd.studentManagement.model.enumeration.EApplicationStatus;
import com.angebhd.studentManagement.model.enumeration.EStudentStatus;
import com.angebhd.studentManagement.repository.AcademicUnitRepository;
import com.angebhd.studentManagement.repository.ApplicationRepository;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private AcademicUnitRepository academicUnitRepository;

    @Autowired
    private StudentService studentService;

    public OperationResult apply(Application application, String code) {

        Optional<AcademicUnit> dept = academicUnitRepository.findByCode(code);
        if (dept.isEmpty() && dept.get().getType().equals(EAcademicUnitType.DEPARTMENT)) {
            return new OperationResult(false, "Invalid department");
        }

        application.setStatus(EApplicationStatus.PENDING);
        application.setDepartment(dept.get());
        try {
            applicationRepository.save(application);
            return new OperationResult(true,
                    "Application received, and is being proccessd, you'll be updated about your application by mail");

        } catch (Exception e) {
            return new OperationResult(false, "Application not saved");
        }
    }

    public OperationResult approve(UUID id) {
        Optional<Application> appl = applicationRepository.findById(id);
        if (appl.isPresent()) {
            Application app = appl.get();
            Student newStudent = new Student();
            newStudent.setAddress(app.getAddress());
            newStudent.setBirthDate(app.getBirthDate());
            newStudent.setDepartment(app.getDepartment());
            newStudent.setEmail(app.getEmail());
            newStudent.setEnrollmentDate(LocalDate.now());
            newStudent.setFirstName(app.getFirstName());
            newStudent.setLastName("");
            newStudent.setPhone(app.getPhone());
            newStudent.setProgram(app.getProgram());
            newStudent.setStatus(EStudentStatus.ACTIVE);
            app.setStatus(EApplicationStatus.APPROVED);
            applicationRepository.save(app);
            return studentService.add(newStudent, app.getDepartment().getCode());
        }

        return new OperationResult(false, "Application Not found");
    }

    public OperationResult reject(UUID id) {
        Optional<Application> a = applicationRepository.findById(id);
        if (a.isPresent()) {
            Application appl = a.get();
            appl.setStatus(EApplicationStatus.REJECTED);
            applicationRepository.save(appl);
            return new OperationResult(true, "Application rejected sucessfully");
        }

        return new OperationResult(false, "Application not found !");
    }

}