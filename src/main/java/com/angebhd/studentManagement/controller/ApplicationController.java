package com.angebhd.studentManagement.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angebhd.studentManagement.DTO.OperationResult;
import com.angebhd.studentManagement.controller.other.GenericController;
import com.angebhd.studentManagement.model.Application;
import com.angebhd.studentManagement.service.ApplicationService;

@RestController
@RequestMapping(name = "application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping(name = "apply")
    public ResponseEntity<?> apply(@RequestBody Application application, @RequestParam String code) {
        OperationResult res = applicationService.apply(application, code);
        return GenericController.getResponse(res, HttpStatus.CREATED, HttpStatus.CONFLICT);
    }

    @PostMapping(name = "approve")
    public ResponseEntity<?> approve(@RequestBody UUID id) {
        return GenericController.getResponse(applicationService.approve(id), HttpStatus.ACCEPTED, HttpStatus.CONFLICT);
    }

    @PostMapping(name = "reject")
    public ResponseEntity<?> reject(@RequestBody UUID id) {
        return GenericController.getResponse(applicationService.reject(id), HttpStatus.ACCEPTED, HttpStatus.CONFLICT);
    }
}