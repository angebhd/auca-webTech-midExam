package com.angebhd.studentManagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angebhd.studentManagement.DTO.AttendaceFormat;
import com.angebhd.studentManagement.DTO.OperationResult;
import com.angebhd.studentManagement.controller.other.GenericController;

import com.angebhd.studentManagement.service.AttendanceService;

@RestController
@RequestMapping(value = "attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping(value = "savelist")
    public ResponseEntity<?> save(@RequestBody List<AttendaceFormat> attendances, @RequestParam UUID courseId){
        OperationResult res = attendanceService.addList(attendances, courseId);
        return GenericController.getResponse(res, HttpStatus.OK, HttpStatus.NOT_FOUND);
    }
    
}