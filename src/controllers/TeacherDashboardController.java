/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import App.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import models.User;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class TeacherDashboardController implements Initializable {
    @FXML
    private JFXComboBox<Label> gradeLevel, section, selectSubjectRecords, selectGradingRecords, gradeLevelClassRating, 
            sectionClassRating, subjectClassRating, teacherClassRating, 
            gradeLevelStudents, sectionStudents, subjectStudents, teacherStudents;
    @FXML
    private JFXComboBox<String> modeComboBox;
    @FXML
    private ImageView logo;
    @FXML
    private JFXTextField selectQuizRecords;
    @FXML
    private JFXListView recordsListview, registrationListview;
    @FXML
    private JFXButton dashboardBtn, attendanceBtn, classRatingBtn, studentsBtn, 
            uploadFileBtn, logoutBtn, editProfile, generateBtn, fileChooserRecords, 
            fileChooserRegistration, uploadBtnRecords, uploadBtnRegistration, 
            accountsBtn, generateBtnClassRating, generateBtnStudents, enableInstructorsBtn, 
            disableInstructorsBtn, resetInstructorsBtn, enableStudentsBtn, 
            disableStudentsBtn, resetStudentsBtn;
    @FXML
    private Pane classRatingPane, studentsPane, attendancePane, uploadFilePane, dashboardPane, header;
    @FXML
    private AnchorPane dropdownBtn, usernameBtn, upBtn, downBtn, forRecordsPane, forRegistrationPane, studentsAnchorPane;
    @FXML
    private Polygon dropdownBtnUp, dropdownBtnDown;
    @FXML
    private Label uploadFileLabel, presentDate, noOfStudentsEnrolled, 
            studentsEnrolledLabel, noOfStudentsPresent, studentsPresentLabel, 
            enrolledPerGradeLevelLabel, welcome, username, menuLabel, quizNoLabel, 
            classRatingChartLabel, firstGradingLabel, 
            secondGradingLabel, thirdGradingLabel, fourthGradingLabel, instructorsAccountsLabel, 
            studentsAccountsLabel, toDateLabel, fromDateLabel;
    @FXML
    private Rectangle container1;
    @FXML
    private BorderPane chart, classRatingBorderPane, firstGradingBorderPane, 
            secondGradingBorderPane, thirdGradingBorderPane, fourthGradingBorderPane;
    @FXML
    private ScrollPane attendanceTablePane, scrollPaneClassRating, scrollPaneStudents, 
            instructorsAccountScrollPane, studentsAccountScrollPane,  scrollPaneStudents1;
    @FXML
    private TableView attendanceTable, firstGradingTable, secondGradingTable,  
            thirdGradingTable, fourthGradingTable, instructorsAccountTable, studentsAccountTable, 
            studentDetailsTable;
        
    @FXML
    private TableColumn firstNameColumn, middleNameColumn, lastNameColumn, lrnColumn, 
            birthdayColumn, emailColumn, nameAttendanceColumn, statusAttendanceColumn, 
            timeInAttendanceColumn, timeOutAttendanceColumn, nameInstructorsAccountColumn, 
            usernameInstructorsAccountColumn, positionInstructorsAccountColumn, 
            nameStudentsAccountColumn, usernameStudentsAccountColumn, gradeLevelStudentsAccountColumn, 
            sectionStudentsAccountColumn;
    
    @FXML
    private BarChart<?, ?> enrolledPerGradeLevelChart;
    @FXML
    private JFXDatePicker dateFromPicker, dateToPicker;
    @FXML
    private Hyperlink linkToCSVFile;
    
    private File selectedFile;
    private Calendar calendar;
    private SimpleDateFormat format;
    private String month, day, time;
    private Date date;

    @FXML
    private JFXRadioButton firstGradingRadioBtn, secondGradingRadioBtn, 
            thirdGradingRadioBtn, fourthGradingRadioBtn;

    private App app;
    private User admin;

    public TeacherDashboardController(User admin){
        this.admin=admin;
    }

    public void setAppInstance(App app){
        this.app = app;
    }


    public void handleLogout(){
        try{
            app.changeSceneTo("login");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        username.setText(admin.getFirstName());
        welcome.setText("Welcome back, "+admin.getFirstName());

        calendar = Calendar.getInstance();

        format = new SimpleDateFormat("MMMM dd yyyy");
        date = calendar.getTime();
        month = format.format(date);

        format = new SimpleDateFormat("EEEE");
        date = calendar.getTime();
        day = format.format(date);

        presentDate.setText(day+", " + month);


        gradeLevel.getItems().add(new Label("Grade 7"));
        gradeLevel.getItems().add(new Label("Grade 8"));
        gradeLevel.getItems().add(new Label("Grade 9"));
        gradeLevel.getItems().add(new Label("Grade 10"));
        section.getItems().add(new Label("1"));
        section.getItems().add(new Label("2"));
        section.getItems().add(new Label("3"));
        section.getItems().add(new Label("4"));
        section.getItems().add(new Label("5"));
        
        //Class Rating
        gradeLevelClassRating.getItems().add(new Label ("Grade 7"));
        gradeLevelClassRating.getItems().add(new Label ("Grade 8"));
        gradeLevelClassRating.getItems().add(new Label ("Grade 9"));
        gradeLevelClassRating.getItems().add(new Label ("Grade 10"));
        sectionClassRating.getItems().add(new Label ("1"));
        sectionClassRating.getItems().add(new Label ("2"));
        sectionClassRating.getItems().add(new Label ("3"));
        sectionClassRating.getItems().add(new Label ("4"));
        sectionClassRating.getItems().add(new Label ("5"));
        
        
        //upload
        modeComboBox.getItems().add(new String ("Upload Records"));
        modeComboBox.getItems().add(new String ("Upload File for Registration"));
        
        dropdownBtn.setVisible(false);
        upBtn.setVisible(false);
        String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
                dashboardBtn.setStyle(style);
    }    

    @FXML
    private void handleButtonAction(MouseEvent event) {
        if (event.getSource() == dashboardBtn){
            header.setVisible(true);
            dashboardPane.setVisible(true);
            dashboardPane.toFront();
                String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
                dashboardBtn.setStyle(style);
                attendanceBtn.setStyle(null);
                classRatingBtn.setStyle(null);
                studentsBtn.setStyle(null);
                uploadFileBtn.setStyle(null);
                
            attendancePane.setVisible(false);
            attendancePane.setVisible(false);
            classRatingPane.setVisible(false);
            studentsPane.setVisible(false);
            uploadFilePane.setVisible(false);

        }
        else if (event.getSource() == attendanceBtn){
            header.setVisible(true);
            dashboardPane.setVisible(false);
            attendancePane.setVisible(true);
            attendancePane.toFront();
                String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
                attendanceBtn.setStyle(style);
                dashboardBtn.setStyle(null);
                classRatingBtn.setStyle(null);
                studentsBtn.setStyle(null);
                uploadFileBtn.setStyle(null);

            classRatingPane.setVisible(false);
            studentsPane.setVisible(false);
            uploadFilePane.setVisible(false);

        }
        else if (event.getSource() == classRatingBtn){
            header.setVisible(true);
            dashboardPane.setVisible(false);
            attendancePane.setVisible(false);
            classRatingPane.setVisible(true);
            classRatingPane.toFront();
                String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
                classRatingBtn.setStyle(style);
                attendanceBtn.setStyle(null);
                dashboardBtn.setStyle(null);
                studentsBtn.setStyle(null);
                uploadFileBtn.setStyle(null);

            studentsPane.setVisible(false);
            uploadFilePane.setVisible(false);

        }
        else if (event.getSource() == studentsBtn){
            header.setVisible(true);
            dashboardPane.setVisible(false);
            attendancePane.setVisible(false);
            classRatingPane.setVisible(false);
            studentsPane.setVisible(true);
            studentsPane.toFront();
                String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
                studentsBtn.setStyle(style);
                attendanceBtn.setStyle(null);
                classRatingBtn.setStyle(null);
                dashboardBtn.setStyle(null);
                uploadFileBtn.setStyle(null);

            uploadFilePane.setVisible(false);

        }
        else if (event.getSource() == uploadFileBtn){
            header.setVisible(true);
            dashboardPane.setVisible(false);
            attendancePane.setVisible(false);
            classRatingPane.setVisible(false);
            studentsPane.setVisible(false);
            uploadFilePane.setVisible(true);
            uploadFilePane.toFront();
                String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
                uploadFileBtn.setStyle(style);
                attendanceBtn.setStyle(null);
                classRatingBtn.setStyle(null);
                studentsBtn.setStyle(null);
                dashboardBtn.setStyle(null);

        }
//        else if (event.getSource() == accountsBtn){
//            header.setVisible(true);
//            dashboardPane.setVisible(false);
//            attendancePane.setVisible(false);
//            classRatingPane.setVisible(false);
//            studentsPane.setVisible(false);
//            accountsPane.setVisible(true);
//            accountsPane.toFront();
//                String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
//                accountsBtn.setStyle(style);
//                attendanceBtn.setStyle(null);
//                classRatingBtn.setStyle(null);
//                studentsBtn.setStyle(null);
//                dashboardBtn.setStyle(null);
//
//        }
//        else if (event.getSource() == accountsBtn){
//            header.setVisible(true);
//            dashboardPane.setVisible(false);
//            attendancePane.setVisible(false);
//            classRatingPane.setVisible(false);
//            studentsPane.setVisible(false);
//            accountsPane.setVisible(true);
//            accountsPane.toFront();
//                String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
//                accountsBtn.setStyle(style);
//                attendanceBtn.setStyle(null);
//                classRatingBtn.setStyle(null);
//                studentsBtn.setStyle(null);
//                dashboardBtn.setStyle(null);
//        }
    }

    @FXML
    private void hide(MouseEvent event) {
        dropdownBtn.setVisible(false);
        downBtn.setVisible(true);
        upBtn.setVisible(false);
    }

    @FXML
    private void show(MouseEvent event) {
        dropdownBtn.setVisible(true);
        downBtn.setVisible(false);
        upBtn.setVisible(true);
    }

    @FXML
    private void handleGenerate(MouseEvent event) {
        System.out.println(dateFromPicker.getValue());
        System.out.println(dateToPicker.getValue());
    }

    @FXML
    private void handleRadioButton(ActionEvent event) {
        if (event.getSource() == firstGradingRadioBtn){
            firstGradingBorderPane.toFront();
            firstGradingBorderPane.setVisible(true);
            secondGradingBorderPane.setVisible(false);
            thirdGradingBorderPane.setVisible(false);
            fourthGradingBorderPane.setVisible(false);
        }
        else if (event.getSource() == secondGradingRadioBtn){
            secondGradingBorderPane.toFront();
            firstGradingBorderPane.setVisible(false);
            secondGradingBorderPane.setVisible(true);
            thirdGradingBorderPane.setVisible(false);
            fourthGradingBorderPane.setVisible(false);
        }
        else if (event.getSource() == thirdGradingRadioBtn){
            thirdGradingBorderPane.toFront();
            firstGradingBorderPane.setVisible(false);
            secondGradingBorderPane.setVisible(false);
            thirdGradingBorderPane.setVisible(true);
            fourthGradingBorderPane.setVisible(false);
        }
        else if (event.getSource() == fourthGradingRadioBtn){
            fourthGradingBorderPane.toFront();
            firstGradingBorderPane.setVisible(false);
            secondGradingBorderPane.setVisible(false);
            thirdGradingBorderPane.setVisible(false);
            fourthGradingBorderPane.setVisible(true);
        }
    }

    @FXML
    private void handleComboBoxAction(ActionEvent event) {
        if (modeComboBox.getValue().equals("Upload Records")){
            forRecordsPane.toFront();
            forRecordsPane.setVisible(true);
            forRegistrationPane.setVisible(false);
        }
        else if (modeComboBox.getValue().equals("Upload File for Registration")){
            forRegistrationPane.toFront();
            forRecordsPane.setVisible(false);
            forRegistrationPane.setVisible(true);
        }
    }

    @FXML
    private void chooseFile(MouseEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("CSV Files", "*.pdf"));
//        File selectedFile = fc.showOpenDialog(null);
        this.selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null){
            recordsListview.getItems().add(selectedFile.getName());
            registrationListview.getItems().add(selectedFile.getName());
        }
        else {
            System.out.println("File is not valid.");
        }
    }

    @FXML
    private void handleUpload(MouseEvent event) {
        System.out.println(selectedFile.getAbsolutePath());
    }
    
}
