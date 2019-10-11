package controllers;

import App.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import helper.PopUp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.User;
import services.LoginService;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private App app;
    private PopUp popUp;


    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton login;
    @FXML
    private User Admin;

    private LoginService loginService = new LoginService();

    public LoginController(User Admin){
        this.Admin = Admin;
    }

    public void handleLogin(){

        String username = user.getText();
        String inputpassword = password.getText();

        HashMap<String, String> user = loginService.GetUser(username, inputpassword);

        if(user.size() > 0){ //check if there is fetched user from database
            Admin.setFirstName(user.get("first_name"));
            Admin.setLastName(user.get("last_name"));
            if(user.get("position").equals("Principal")) {
                try {
                    app.changeSceneTo("AdminDashboard");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try{
                    app.changeSceneTo("TeacherDashboard");
                }catch (Exception e){
                    e.printStackTrace();
                }
//                System.out.println("You are not a principal");
            }
        } else {
            app.DisplayError("Account not Registered");
        }

    }

    public void handleCreate(){
        try {
            app.changeSceneTo("UserRegistration");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TO DO
    }

    public void setAppInstance(App app){
        this.app=app;
    }
//
//    class loginListener implements ActionListener{
//        @Override
//        public void actionPerformed(ActionEvent actionEvent) {
//            user.setEmployeeId("loginView.userId.getText()");
//            user.setPassword("loginView.password.getText()");
//
//            if(user.getTeacher()){
//                //go to dashboard
//            }
//
//        }
//    }
    public void handleEnter (KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) handleLogin();
    }

}
