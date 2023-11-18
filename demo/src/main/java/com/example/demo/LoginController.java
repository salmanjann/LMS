package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
   @FXML
    private Button loginCancelButton;
   @FXML
    private Label loginLoginMessage;
   @FXML
   private TextField loginUsernameField;
   @FXML
   private PasswordField loginPasswordField;

   public  void loginAction(ActionEvent e){
       if(loginUsernameField.getText().isBlank() == false && loginPasswordField.getText().isBlank() == false){
            validateLogin();
       }
       else if(loginUsernameField.getText().isBlank() == true && loginPasswordField.getText().isBlank() == true){
           loginLoginMessage.setText("fields requried");
       }
       else if(loginUsernameField.getText().isBlank() == true){
           loginLoginMessage.setText("username required");
       }
       else if(loginPasswordField.getText().isBlank() == true){
           loginLoginMessage.setText("password required");
       }
   }
   public void cancelAction(ActionEvent e){
       Stage stage = (Stage) loginCancelButton.getScene().getWindow();
       stage.close();
   }

    private void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        // Check if the connection is successful
        if (connectDB != null) {
            String verifyLogin = "SELECT COUNT(1) FROM loginTable WHERE username = '" + loginUsernameField.getText() + "' AND `password` = '" + loginPasswordField.getText() + "'";

            try {
                Statement statement = connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);

                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        loginLoginMessage.setText("Welcome!");
                    } else {
                        loginLoginMessage.setText("Invalid Login!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            loginLoginMessage.setText("Connection failed. Check database settings.");
        }
    }
}