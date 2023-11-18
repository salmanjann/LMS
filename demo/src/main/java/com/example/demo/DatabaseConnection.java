package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
public class DatabaseConnection {
    public Connection DatabaseLink;

    public  Connection getConnection(){
        String dbName = "testing_login";
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
}
