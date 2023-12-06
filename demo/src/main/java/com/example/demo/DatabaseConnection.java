package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public Connection DatabaseLink;

    private static DatabaseConnection _instance = null;

    private DatabaseConnection()
    {

    }

    public  Connection getConnection(){
        String dbName = "LMS";
        String dbUser = "root";
        String dbPassword = "root";
        String url = "jdbc:mysql://localhost:3306/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DatabaseLink = DriverManager.getConnection(url,dbUser,dbPassword);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return DatabaseLink;
    }

    public static DatabaseConnection getInstance()
    {
        if (_instance == null )
            _instance = new DatabaseConnection();

        return _instance;
    }
}
