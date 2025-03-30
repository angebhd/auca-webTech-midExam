package com.angebhd.studentManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angebhd.studentManagement.service.FeesService;

@RestController
@RequestMapping(value = "fees")
public class FeesController {

    @Autowired
    private FeesService feesService;
    
}