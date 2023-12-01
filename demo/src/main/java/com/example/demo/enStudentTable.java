package com.example.demo;

import javafx.scene.control.Button;

public class enStudentTable {
    private String  courseName;
    private Button  requestButton;

    public enStudentTable(String _courseName, Button _button){
        courseName = _courseName;
        requestButton = _button;
    }
    public String getCourseName() {
        return courseName;
    }

    public Button getRequestButton() {
        return requestButton;
    }
}
