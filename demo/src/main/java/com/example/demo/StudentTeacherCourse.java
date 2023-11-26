package com.example.demo;

public class StudentTeacherCourse {
    private int id;
    private int userid;
    private int teachercourseid;

    public StudentTeacherCourse() {
        // Default constructor
    }

    public StudentTeacherCourse(int userid, int teachercourseid) {
        this.userid = userid;
        this.teachercourseid = teachercourseid;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getTeachercourseid() {
        return teachercourseid;
    }

    public void setTeachercourseid(int teachercourseid) {
        this.teachercourseid = teachercourseid;
    }
}
