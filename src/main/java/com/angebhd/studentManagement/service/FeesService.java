package com.angebhd.studentManagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.angebhd.studentManagement.model.Course;
import com.angebhd.studentManagement.model.Fees;
import com.angebhd.studentManagement.model.OfferedCourse;
import com.angebhd.studentManagement.model.Student;
import com.angebhd.studentManagement.model.StudentRegistration;
import com.angebhd.studentManagement.repository.FeesRepository;

@Service
public class FeesService {

    @Autowired
    private FeesRepository feesRepository;

    public boolean create(Student student, StudentRegistration studentRegistration) {
        Fees fees = new Fees();

        fees.setStudent(student);
        fees.setRegistration(studentRegistration);

        List<OfferedCourse> offeredCourses = studentRegistration.getCourses();
        int amount = 0;
        int creditPrice = 17869;

        for (OfferedCourse o : offeredCourses) {
            Course c = o.getCourse();
            int temp = creditPrice * (int) c.getCredit();
            amount += temp;
        }
        fees.setAmount(amount);
        fees.setDate(LocalDate.now());
        feesRepository.save(fees);
        return true;
    }

    public boolean update(Student student, StudentRegistration studentRegistration) {
        Optional<Fees> f = feesRepository.findByStudentAndRegistration(student, studentRegistration);

        if (f.isPresent()) {

            Fees fees = f.get();

            List<OfferedCourse> offeredCourses = studentRegistration.getCourses();
            int amount = 0;
            int creditPrice = 17869;

            for (OfferedCourse o : offeredCourses) {
                Course c = o.getCourse();
                int temp = creditPrice * (int) c.getCredit();
                amount += temp;
            }
            fees.setAmount(amount);
            fees.setDate(LocalDate.now());
            feesRepository.save(fees);

            return true;
        } else {
            // return this.create(student, studentRegistration);
            return false;
        }

    }

}