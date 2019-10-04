package services;

import datasource.Database;
import helper.Encryption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class LoginService {

    private Connection conn;
    private Database database = new Database();
    private Encryption encryption;

    public LoginService(){
        this.conn = database.getConn();
        try{
            encryption = new Encryption();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public HashMap<String, String> GetUser(String username, String password){
        HashMap<String, String> user = new HashMap<>();
        String query = "SELECT first_name, last_name, position FROM accounts WHERE username LIKE ? AND password LIKE ?";
        try {
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, username);
            prepSt.setString(2, encryption.hashPassword(password));
            ResultSet resultSet = prepSt.executeQuery();

            while (resultSet.next()) {
                user.put("first_name", resultSet.getString("first_name"));
                user.put("last_name",resultSet.getString("last_name"));
                user.put("position", resultSet.getString("position"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getTeacher()");
        }
//
//        System.out.println(user);

        return user;
    }


}
