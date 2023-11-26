package com.example.demo;

import java.sql.Connection;

public class ApplicationState {
    public static Admin currentlyLoggedAdmin;
    public static Teacher currentlyLoggedTeacher;
    public static Student currentlyLoggedStudent;

    public static DatabaseConnection connectNow;
    public static Connection connectDB;
}
