package com.example.demo;

public class TeacherCourse {
    private int userId;
    private int courseId;

    public TeacherCourse() {
    }

    public TeacherCourse(int userId, int courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "TeacherCourse{" +
                "userId=" + userId +
                ", courseId=" + courseId +
                '}';
    }
}