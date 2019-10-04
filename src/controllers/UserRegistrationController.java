package controllers;

import App.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.User;
import services.RegistrationService;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRegistrationController implements Initializable {

    ObservableList<String> positionList = FXCollections.observableArrayList("Principal","Assistant Principal","Head User","Registar","Administrative Staff","User - Science Department"
            ,"User - Filipino Department","User - English Department","User - Mathematics Department","User - MAPEH Department","User - ESP Department",
            "User - AP Department", "User - TLE Department","Facilitative Staff","Guest");

    private App app;

    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private TextField employeeId;
    @FXML private ComboBox  position;
    @FXML private DatePicker birthday;
    @FXML private TextField email;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private PasswordField confirm;
    @FXML private Label messageLabel;

    private User user;
    private RegistrationService registrationService;

    public UserRegistrationController(User user){
        this.user = user;
        this.registrationService = new RegistrationService(user);
    }

    private void clearInputs(){
        firstName.setText("");
        lastName.setText("");
        employeeId.setText("");
        email.setText("");
        username.setText("");
        password.setText("");
        confirm.setText("");
    }

    @FXML
    public void handleSubmit(){
        if(confirm.getText().equals(password.getText())) {
                user.setFirstName(firstName.getText());
                user.setLastName(lastName.getText());
                user.setEmployeeId(employeeId.getText());
                user.setPosition((String) position.getValue());
                user.setBirthday(String.valueOf(birthday.getValue()));
                user.setUsername(username.getText());
                user.setEmail(email.getText());
                user.setPassword(password.getText());

            try {
                if (registrationService.RegisterUser()) {
//                    app.changeSceneTo("login");
                    messageLabel.setText("Successfully Registered!");
                    clearInputs();
                } else {
                    messageLabel.setText("Invalid Registration!");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            DisplayError("Password does not match!");
        }
    }

    public void DisplayError(String message){
        System.out.println(message);
    }

    @FXML
    public void handleCancel(){
        try{
            app.changeSceneTo("login");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setAppInstance(App app){
        this.app = app;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        position.getItems().addAll(positionList);
    }

}
