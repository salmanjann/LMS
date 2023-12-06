package com.example.demo;

public class StudentSection {

    private int _id;
    private String _sectionName;
    private String _courseName;

    public StudentSection(int id, String sectionName, String courseName)
    {
        _id = id;
        _courseName = courseName;
        _sectionName = sectionName;
    }

    public int getId()
    {
        return _id;
    }

    public String getSectionName()
    {
        return _sectionName;
    }

    public String getCourseName()
    {
        return _courseName;
    }

    @Override
    public String toString() {
        return _courseName + " " + _sectionName;
    }
}
