package com.example.demo;

public class SectionTeacherCourse {
    private int id;
    private int sectionid;
    private String sectionName;
    private int teachercourseid;

    public SectionTeacherCourse() {
        // Default constructor
    }

    public SectionTeacherCourse(int sectionid, String sectionName, int teachercourseid) {
        this.sectionid = sectionid;
        this.sectionName = sectionName;
        this.teachercourseid = teachercourseid;
    }

    public int getId() {
        return id;
    }

    public int getSectionid() {
        return sectionid;
    }

    public void setSectionid(int sectionid) {
        this.sectionid = sectionid;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getTeachercourseid() {
        return teachercourseid;
    }

    public void setTeachercourseid(int teachercourseid) {
        this.teachercourseid = teachercourseid;
    }
}
