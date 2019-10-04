package services;

import datasource.Database;
import helper.Encryption;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegistrationService {


    private User user;
    private Encryption encryption;
    private Database database = new Database();
    private Connection conn;

    public RegistrationService(User user){
        this.user = user;
        this.conn = database.getConn();
        try {
            encryption = new Encryption();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean RegisterUser(){

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String employeeId = user.getEmployeeId();
        String position = user.getPosition();
        String birthday = user.getBirthday();
        String email = user.getEmail();
        String username = user.getUsername();
        String passwordInput = user.getPassword();

        String password = encryption.hashPassword(passwordInput);

        String query = "INSERT INTO accounts(first_name, last_name, employee_id, position, birthday, username, email, password)" +
                "VALUES (?,?,?,?,?,?,?,?)";

        boolean action=false;

        try {

            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, firstName);
            prepSt.setString(2, lastName);
            prepSt.setString(3, employeeId);
            prepSt.setString(4, position);
            prepSt.setString(5, birthday);
            prepSt.setString(6, username);
            prepSt.setString(7, email);
            prepSt.setString(8, password);

            int i = prepSt.executeUpdate();

            action = (i > 0);


        }catch (Exception e){

            e.printStackTrace();
            System.out.println("Error in createTeacher()");
        }

        return action;
    }

}
