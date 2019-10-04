package models;

import datasource.Database;

import java.util.ArrayList;

public class User {

    private Database database = new Database();

    private String firstName;
    private String lastName;
    private String employeeId;
    private String department;
    private String position;
    private String subjectHandle;
    private String birthday;
    private String username;
    private String email;
    private String password;



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPosition() {
        return position;
    }

    public String getBirthday() {
        return birthday;
    }

    public boolean updateFirstName(){
        return database.updateTeacherFirstName(this.employeeId, this.firstName);
    }

    public boolean updateLastName(){
        return database.updateTeacherLastName(this.employeeId, this.lastName);
    }

    public boolean updatePassword(){
        return database.updateTeacherPassword(this.employeeId, this.password);
    }


}
