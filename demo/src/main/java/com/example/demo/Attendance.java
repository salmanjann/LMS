package com.example.demo;

import java.time.LocalDate;

public class Attendance {

    private LocalDate attendanceDate;
    private boolean isPresent;
    private String attendanceStatus;

    public Attendance( LocalDate attendanceDate, boolean isPresent) {
        this.attendanceDate = attendanceDate;
        this.isPresent = isPresent;
        if (isPresent)
            attendanceStatus = "Present";
        else attendanceStatus = "Absent";
    }

     // Getter for _attendanceDate
    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    // Getter for _isPresent
    public boolean getIsPresent() {
        return isPresent;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }
}
