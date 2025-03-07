package com.angebhd.studentManagement.model.others;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter

public class OperationResult {

    private String message;
    private boolean success;

    public OperationResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public OperationResult(){}
}