package com.example.demo;

import javafx.scene.control.Button;

public class FeedbackTable {
    private String courseName;
    private String status;
    private Button  button;
    public FeedbackTable(String _courseName,String _status,Button _button){
        courseName = _courseName;
        status = _status;
        button = _button;
    }

    public Button getButton() {
        return button;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getStatus() {
        return status;
    }
}
