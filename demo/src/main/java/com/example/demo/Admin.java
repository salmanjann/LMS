package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

public class Admin extends User {
    public Admin() {
        super();
    }

    // No attributes now
    public Admin(int _id, String _name, String _username, String _email, String _password) {
        super(_id, _name, _username, _email, _password);
    }
    public String addTeacher(String _name, String _email, String _username, String _password) {
        String isTeacherExist = "SELECT COUNT(1) FROM `TEACHER` WHERE username = '" + _username + "' OR email = '" + _email + "'";
        String insertTeacher = "insert into `TEACHER`(`name`, username,email,`password`) value ('" + _name + "','" + _username + "','" + _email + "','" + _password + "');";

        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(isTeacherExist);


            if (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return "Teacher Exists";
                } else {
                    Statement statement2 = ApplicationState.connectDB.createStatement();
                    statement2.executeUpdate(insertTeacher);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "New Teacher Added";
    }
    public String addStudent(String _name, String _email, String _username, String _password,String _rollNo) {
        String isStudentExist = "SELECT COUNT(1) FROM `STUDENT` WHERE username = '" + _username + "' OR email = '" + _email + "'";
        String insertStudent = "insert into `STUDENT`(`name`, username,email,`password`,rollNo) value ('" + _name + "','" + _username + "','" + _email + "','"+ _password + "','" + _rollNo + "');";

        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(isStudentExist);


            if (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return "Student Exists";
                } else {
                    Statement statement2 = ApplicationState.connectDB.createStatement();
                    statement2.executeUpdate(insertStudent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "New Student Added";
    }
    public String addCourse(String _name, String _desc, String _id) {
        String isCourseExist = "SELECT COUNT(1) FROM Course WHERE courseName = '" + _name + "' OR courseId = '"+_id+"';";
        String insertCourse = "insert into Course(courseId,courseName, courseDescription) value ('" + _id + "','"+ _name + "','" + _desc + "');";
        String makeNewSection = "insert into courseSection(courseName,teacherName) value('"+ _name + "','NULL');";
        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(isCourseExist);


            if (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return "Course Exists";
                } else {
                    Statement statement2 = ApplicationState.connectDB.createStatement();
                    statement2.executeUpdate(insertCourse);

                    Statement statement3 = ApplicationState.connectDB.createStatement();
                    statement3.executeUpdate(makeNewSection);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "New Course Added";
    }
    public void approveBtn(String studentName,String courseName){
        String getLatestSection = "SELECT sectionName FROM courseSection WHERE courseName = '"+courseName+"' ORDER BY sectionName DESC LIMIT 1;";
        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(getLatestSection);


            if (queryResult.next()) {
                String latestSection = queryResult.getString(1);
                String getCountOfStudents = "SELECT COUNT(*) AS studentCount FROM studentSections WHERE courseSec = '"+latestSection+"' AND courseName = '"+courseName+"';";
                try {
                    Statement statement2 = ApplicationState.connectDB.createStatement();
                    ResultSet queryResult2 = statement2.executeQuery(getCountOfStudents);


                    if (queryResult2.next()) {
                        int count = queryResult2.getInt(1);
                        String sectionToEnroll = "";
                        if(count >=3 ){
                            String makeNewSection = "insert into courseSection(courseName,teacherName) value('"+ courseName + "','NULL');";
                            try {
                                Statement statement3 = ApplicationState.connectDB.createStatement();
                                statement3.executeUpdate(makeNewSection);
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        String section = "SELECT sectionName FROM courseSection WHERE courseName = '"+courseName+"' ORDER BY sectionName DESC LIMIT 1;";
                        try {
                            Statement statement3 = ApplicationState.connectDB.createStatement();
                            ResultSet queryResult3 = statement3.executeQuery(section);

                            if (queryResult3.next()) {
                                sectionToEnroll = queryResult3.getString(1);
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        // now enroll student
                        String enrollStudent = "insert into studentSections(studentName,courseSec,courseName) value('"+ studentName+"','"+sectionToEnroll+"','"+courseName+"');";
                        try {
                            Statement statement3 = ApplicationState.connectDB.createStatement();
                            statement3.executeUpdate(enrollStudent);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        // now remove student from requestingList
                        String deleteRecordFromRequesting = "DELETE FROM requestingCourse WHERE studentName = '"+studentName+"' AND courseName = '"+courseName+"';";
                        try {
                            Statement statement4 = ApplicationState.connectDB.createStatement();
                            statement4.executeUpdate(deleteRecordFromRequesting);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String assignCourseToTeacher(String courseName, String teacherName,String sectionName) {
        String updateCourseSectionTable = "UPDATE courseSection SET teacherName = '"+teacherName+"' WHERE sectionName = '"+sectionName+"' AND courseName = '"+courseName+"';";
        String isCourseAssigned = "select teacherName from courseSection where courseName = '"+courseName+"' AND sectionName = '"+sectionName+"';";
        try {
            Statement statement = ApplicationState.connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(isCourseAssigned);


            if (queryResult.next()) {
                String result = queryResult.getString(1);
                if (!"NULL".equalsIgnoreCase(result)) {
                    return "Course Already Assigned";
                } else {
                    try {
                        Statement statement2 = ApplicationState.connectDB.createStatement();
                        statement2.executeUpdate(updateCourseSectionTable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Course Assigned";
    }
    public ObservableList<Course> getCourses(String sql) throws SQLException {
        Statement statement = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);

        ObservableList<Course> courses = FXCollections.observableArrayList();

        while (queryResult.next()) {
            Course course = new Course(
                    queryResult.getString("courseId"),
                    queryResult.getString("courseName"),
                    queryResult.getString("courseDescription")
            );
            courses.add(course);
        }
        return courses;
    }
    public ObservableList<Teacher> getTeachers(String sql) throws SQLException {
        Statement statement = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);

        ObservableList<Teacher> teachers = FXCollections.observableArrayList();

        while (queryResult.next()) {
            Teacher teacher = new Teacher(
                    queryResult.getInt("id"),
                    queryResult.getString("name"),
                    queryResult.getString("username"),
                    queryResult.getString("email"),
                    queryResult.getString("password")
            );
            teachers.add(teacher);
        }
        return teachers;
    }
    public ObservableList<courseSection> getCourseSections(String sql) throws SQLException {
        Statement statement = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);

        ObservableList<courseSection> sections = FXCollections.observableArrayList();

        while (queryResult.next()) {
            courseSection courseSec = new courseSection(
                    queryResult.getString("sectionName"),
                    queryResult.getString("courseName"),
                    queryResult.getString("teacherName")
            );
            sections.add(courseSec);
        }
        return sections;
    }
    public ObservableList<Student> getStudents(String sql) throws SQLException {
        Statement statement = ApplicationState.connectDB.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);

        ObservableList<Student> students = FXCollections.observableArrayList();

        while (queryResult.next()) {
            Student student = new Student(
                    queryResult.getInt("id"),
                    queryResult.getString("name"),
                    queryResult.getString("username"),
                    queryResult.getString("email"),
                    queryResult.getString("password"),
                    queryResult.getString("rollNo")
            );
            students.add(student);
        }
        return students;
    }
    public String approveStudentApplication(int courseId, int teacherId, ObservableList<Student> students) throws SQLException {
        int sectionCount = 0;
        String approveApplicationsCall = "{CALL ApproveStudentsApplications(?,?,?)}";
        CallableStatement statement = ApplicationState.connectDB.prepareCall(approveApplicationsCall);
        int count = 0;
        for (int i = 0; i < students.stream().count();i++)
        {
            Student student = students.get(i);
            statement.setInt(1, teacherId);
            statement.setInt(2, courseId);
            statement.setInt(3, student.getId());

            statement.executeQuery();
        }

        return "Application Approved";
    }
}