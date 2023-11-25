package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


   @FXML
    private Button loginCancelButton;
   @FXML
    private Label loginLoginMessage;
   @FXML
   private TextField loginUsernameField;
   @FXML
   private PasswordField loginPasswordField;

   @FXML
   private RadioButton loginRadioAdmin,loginRadioTeacher,loginRadioStudent;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ApplicationState.connectNow = new DatabaseConnection();
        ApplicationState.connectDB = ApplicationState.connectNow.getConnection();
    }

   private void switchToAdminScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("AdminDashboard.fxml"));
        Parent root = fxmlLoader.load();

        Stage adminStage = new Stage();  // Create a new Stage
        adminStage.initStyle(StageStyle.UNDECORATED);
        adminStage.setScene(new Scene(root, 520, 400));
        adminStage.show();
        Stage stage = (Stage) loginCancelButton.getScene().getWindow();
        stage.close();
   }

   private void switchToTeacherScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("TeacherDashboard.fxml"));
        Parent root = fxmlLoader.load();

        Stage teacherStage = new Stage();  // Create a new Stage
        teacherStage.initStyle(StageStyle.UNDECORATED);
        teacherStage.setScene(new Scene(root, 520, 400));
        teacherStage.show();
   }

   private void switchToStudentScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("StudentDashboard.fxml"));
        Parent root = fxmlLoader.load();

        Stage studentStage = new Stage();  // Create a new Stage
        studentStage.initStyle(StageStyle.UNDECORATED);
        studentStage.setScene(new Scene(root, 520, 400));
        studentStage.show();
   }
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
        // Check if the connection is successful
        if (ApplicationState.connectDB != null) {
            String table = "`ADMIN`";
            if(loginRadioAdmin.isSelected() == true){
                table = "`ADMIN`";
            }
            else if(loginRadioTeacher.isSelected() == true){
                table = "`TEACHER`";
            }
            else if(loginRadioStudent.isSelected() == true){
                table = "`STUDENT`";
            }

            String verifyLogin = "SELECT COUNT(1) FROM " + table + " WHERE (username = '" + loginUsernameField.getText() + "' OR email = '" + loginUsernameField.getText() + "')AND `password` = '" + loginPasswordField.getText() + "'";


            try {
                Statement statement = ApplicationState.connectDB.createStatement();
                ResultSet queryResult = statement.executeQuery(verifyLogin);

                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        if(loginRadioAdmin.isSelected() == true){
                            loginLoginMessage.setText("");
                            createAdmin();
                            // admin details
                            // admin object
                            // applicationstate = admin object
                            switchToAdminScene();
                        }
                        else if(loginRadioTeacher.isSelected() == true){
                            loginLoginMessage.setText("");
                            switchToTeacherScene();
                        }
                        else if(loginRadioStudent.isSelected() == true){
                            loginLoginMessage.setText("");
                            switchToStudentScene();
                        }

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
    private  void createAdmin(){
        String getAdmin = "SELECT * FROM `ADMIN` WHERE username = '" + loginUsernameField.getText() + "'";


        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getAdmin);
            if (queryResult.next()) {
                int id = queryResult.getInt(1);
                String name = queryResult.getString(2);
                String username = queryResult.getString(3);
                String email = queryResult.getString(4);
                String password = queryResult.getString(5);
                Admin admin = new Admin(id, name,username,email,password);
                ApplicationState.currentlyLoggedIn = admin;
//                ApplicationState.currentlyLoggedIn.addTeacher();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}