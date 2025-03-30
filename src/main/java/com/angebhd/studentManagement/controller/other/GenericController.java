package com.angebhd.studentManagement.controller.other;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.angebhd.studentManagement.model.others.OperationResult;

public class GenericController {

    public static ResponseEntity<?> getResponse(OperationResult op, HttpStatus success, HttpStatus failure){
        if (op.isSuccess()) {

            return new ResponseEntity<>(op.getMessage(), success);
        }else{
            return new ResponseEntity<>(op.getMessage(), failure);
        }
    }
}