package com.example.demo;

public class courseSection {
    private String sectionName;
    private String  courseName;
    private String teacherName;
    private int id;

    public courseSection(String _sectionName,String _courseName,String _teacherName){
        sectionName = _sectionName;
        courseName = _courseName;
        teacherName = _teacherName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSectionName() {
        return sectionName;
    }
    @Override
    public String toString() {
        return sectionName;
    }

    public String getCourseSection() {return "'" + courseName + "', '" + sectionName + "'" ;}
}
