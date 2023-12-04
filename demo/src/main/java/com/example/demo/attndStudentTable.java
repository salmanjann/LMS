package com.example.demo;

import javafx.scene.control.Button;

public class attndStudentTable {
    private final String studentName;
    private final String rollNo;

    private Button  presentButton;

    private Button  absentButton;


    public attndStudentTable(int id, String studentName, String rollNo, Button _presentButton, Button _absentButton)
    {
        this.studentName = studentName;
        this.rollNo = rollNo;
        this.presentButton = _presentButton;
        this.absentButton = _absentButton;
    }

    public String getStudentName() {return studentName;}
    public String getRollNo() {return rollNo;}
    public Button getPresentButton() {
        return presentButton;
    }
    public Button getAbsentButton() {return  absentButton; }

}

