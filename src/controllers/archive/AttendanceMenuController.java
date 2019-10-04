/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.archive;

import App.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Jhopat Santiago
 */
public class AttendanceMenuController implements Initializable {
    private App app;

    @FXML
    private ImageView webCam;
    @FXML
    private Button timeIn;
    @FXML
    private Button timeOut;
    @FXML
    private Button addHolidaySuspension;
    @FXML
    private Button viewRecord;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setAppInstance(App app){
        this.app = app;
    }

    public void handleTimeIn(){
        try {
            app.changeSceneTo("attendanceTimeIn");
        }catch (Exception e){

        }
    }

    public void handleTimeOut(){
        try {
            app.changeSceneTo("attendanceTimeOut");
        }catch (Exception e){

        }
    }


    public void handleHoliday(){
        try {
            app.changeSceneTo("AttendanceHoliday");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void handleViewRecord(){
        try{
            app.changeSceneTo("AttendanceViewer");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void handleBack(){
        try {
            app.changeSceneTo("LandingPage");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
