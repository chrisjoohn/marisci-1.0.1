package controllers;

import App.App;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import models.User;
import views.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable {

    private App app;
    private ViewFactory viewFactory;
    private User user;

    @FXML
    private ImageView bg;
    @FXML
    private JFXButton dashboard;
    @FXML
    private JFXButton attendance;
    @FXML
    private JFXButton teachers_rating;
    @FXML
    private JFXButton class_rating;
    @FXML
    private JFXButton students;
    @FXML
    private Label profile;
    @FXML
    private AnchorPane buttonDown;
    @FXML
    private Polygon dropdown_button;
    @FXML
    private AnchorPane buttonUp;
    @FXML
    private Polygon dropdown_buttonUp;
    @FXML
    private AnchorPane dropdown;
    @FXML
    private JFXButton edit_profile;
    @FXML
    private JFXButton log_out;

    public MenuController(User user){
        this.user = user;
    }

//    @FXML
//    public void show(){
//        dropdown.setVisible(true);
//        buttonDown.setVisible(false);
//        buttonUp.setVisible(true);
//    }
//
//    @FXML
//    public void hide(){
//        dropdown.setVisible(false);
//        buttonDown.setVisible(true);
//        buttonUp.setVisible(false);
//    }

    public void handleMindMappingClick(){
        try {
            app.changeSceneTo("login");
        }catch (Exception e){

        }
    }

    public void handleAttendanceClick() {
        try {
            app.changeSceneTo("ScanQR");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setAppInstance(App app){
        this.app=app;
    }

    public void setViewFactoryInstance(ViewFactory viewFactory){
        this.viewFactory = viewFactory;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        dropdown.setVisible(false);
//        buttonUp.setVisible(false);
//
//        profile.setText(user.getFirstName());
//        profile.getItems().add("Edit Profile");
//        profile.getItems().add("Log Out");
    }
}
