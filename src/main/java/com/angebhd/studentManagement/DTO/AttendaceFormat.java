package com.angebhd.studentManagement.DTO;
import com.angebhd.studentManagement.model.enumeration.EAttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendaceFormat {

    private int id;
    private EAttendanceStatus status;


}