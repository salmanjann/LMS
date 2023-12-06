package com.example.demo;

public class viewFeedbackTable {
    private String rollNo;
    private String Q1;
    private String Q2;
    private String Q3;

    public viewFeedbackTable(String _rollNo,String _Q1, String _Q2, String _Q3){
        rollNo = _rollNo;
        Q1  = _Q1;
        Q2   = _Q2;
        Q3 = _Q3;
    }

    public String getRollNo() {
        return rollNo;
    }

    public String getQ1() {
        return Q1;
    }

    public String getQ2() {
        return Q2;
    }

    public String getQ3() {
        return Q3;
    }
}
