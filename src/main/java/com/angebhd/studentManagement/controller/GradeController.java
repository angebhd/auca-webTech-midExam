package com.angebhd.studentManagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angebhd.studentManagement.controller.other.GenericController;
import com.angebhd.studentManagement.model.others.GradeFormat;
import com.angebhd.studentManagement.model.others.OperationResult;
import com.angebhd.studentManagement.service.GradeService;

@RestController
@RequestMapping(value = "grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping(value = "addcourse")
    public ResponseEntity<?> addCourse(@RequestBody List<GradeFormat> grades, @RequestParam UUID courseId){
        OperationResult res = gradeService.addCourse(grades, courseId);
        return GenericController.getResponse(res, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }

    @GetMapping(value= "get")
    public ResponseEntity<?> get (){
        return new ResponseEntity<>(gradeService.get(), HttpStatus.OK);
    }
    @GetMapping(value= "get/student")
    public ResponseEntity<?> getStudent (@RequestParam int id){
        return new ResponseEntity<>(gradeService.getStudent(id), HttpStatus.OK);
    }
}