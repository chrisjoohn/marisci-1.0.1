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
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import models.Student;
import models.User;
import services.DashboardServices;
import services.StudentServices;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Acer
 */
public class AdminDashboardController implements Initializable {
    @FXML
    private JFXComboBox<Label> gradeLevel, section, gradeLevelClassRating,
            sectionClassRating, subjectClassRating, teacherClassRating,
            gradeLevelStudents, sectionStudents, subjectStudents, teacherStudents;

    @FXML
    private ImageView logo;

    @FXML
    private JFXButton dashboardBtn, attendanceBtn, classRatingBtn, studentsBtn,
            accountsBtn, logoutBtn, editProfile, generateBtn,
            generateBtnClassRating, generateBtnStudents, enableInstructorsBtn,
            disableInstructorsBtn, resetInstructorsBtn, enableStudentsBtn,
            disableStudentsBtn, resetStudentsBtn;

    @FXML
    private Pane classRatingPane, studentsPane, attendancePane, accountsPane,
            dashboardPane, header;

    @FXML
    private AnchorPane dropdownBtn, usernameBtn, upBtn, downBtn, studentsAnchorPane;

    @FXML
    private JFXRadioButton firstGradingRadioBtn, secondGradingRadioBtn,
            thirdGradingRadioBtn, fourthGradingRadioBtn;

    @FXML
    private Polygon dropdownBtnUp, dropdownBtnDown;

    @FXML
    private Label presentDate, noOfStudentsEnrolled, studentsEnrolledLabel,
            noOfStudentsPresent, studentsPresentLabel, enrolledPerGradeLevelLabel,
            welcome, username, menuLabel, classRatingChartLabel, firstGradingLabel,
            secondGradingLabel, thirdGradingLabel, fourthGradingLabel, instructorsAccountsLabel,
            studentsAccountsLabel;

    @FXML
    private Rectangle container1;

    @FXML
    private BorderPane chart, classRatingBorderPane, firstGradingBorderPane,
            secondGradingBorderPane, thirdGradingBorderPane, fourthGradingBorderPane;

    @FXML
    private ScrollPane attendanceTablePane, scrollPaneClassRating, scrollPaneStudents,
            instructorsAccountScrollPane, studentsAccountScrollPane,  scrollPaneStudents1;

//    @FXML
//    private TableView studentsAccountTable, attendanceTable, firstGradingTable, secondGradingTable,
//            thirdGradingTable, fourthGradingTable, instructorsAccountTable;

    @FXML
    private TableView<Student> studentDetailsTable;

//    @FXML
//    private TableColumn<Student, String> middleNameColumn, lastNameColumn, lrnColumn,
//            birthdayColumn, emailColumn, nameAttendanceColumn, statusAttendanceColumn,
//            timeInAttendanceColumn, timeOutAttendanceColumn, nameInstructorsAccountColumn,
//            usernameInstructorsAccountColumn, positionInstructorsAccountColumn,
//            nameStudentsAccountColumn, usernameStudentsAccountColumn, gradeLevelStudentsAccountColumn,
//            sectionStudentsAccountColumn;


    @FXML
    private TableColumn<Student, String> firstNameColumn;

    @FXML
    private TableColumn<Student, String> middleNameColumn;

    @FXML
    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private TableColumn<Student, String> lrnColumn;

    @FXML
    private TableColumn<Student, String> birthdayColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;


    @FXML
    private BarChart<?, ?> enrolledPerGradeLevelChart, classRatingChart;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private Hyperlink linkToCSVFile;


    @FXML
    private CategoryAxis yAxis;

    @FXML
    private NumberAxis xAxis;


    private App app;

    private File selectedFile;
    private StudentServices studentServices = new StudentServices();
    private DashboardServices ds = new DashboardServices();
    private Calendar calendar;
    private SimpleDateFormat format;
    private String month, day, time;
    private Date date;


    private User admin;

    public AdminDashboardController(User admin){
        this.admin = admin;
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



        noOfStudentsEnrolled.setText(String.valueOf(ds.totalNumOfStudents()));
        noOfStudentsPresent.setText(String.valueOf(ds.totalNumOfPresent()));

        XYChart.Series series1 = new XYChart.Series<>();

        series1.getData().add(new XYChart.Data("Grade 7", ds.totalNumStudPerGradeLevel(7)));
        series1.getData().add(new XYChart.Data("Grade 8", ds.totalNumStudPerGradeLevel(8)));
        series1.getData().add(new XYChart.Data("Grade 9", ds.totalNumStudPerGradeLevel(9)));
        series1.getData().add(new XYChart.Data("Grade 10", ds.totalNumStudPerGradeLevel(10)));

        enrolledPerGradeLevelChart.getData().addAll(series1);

        //attendance
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
//        modeComboBox.getItems().add(new String ("Upload Records"));
//        modeComboBox.getItems().add(new String ("Upload File for Registration"));

        dropdownBtn.setVisible(false);
        upBtn.setVisible(false);
        String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
        dashboardBtn.setStyle(style);



//        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("first_name"));
//        middleNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("middle_name"));
//        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("last_name"));
//        lrnColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("lrn"));
//        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("bday"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("Email"));

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("middleName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));
        lrnColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("LRN"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("birthday"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));

        studentDetailsTable.setItems(studentServices.GetStudents());
//        System.out.println(studentServices.GetStudents());

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
            accountsBtn.setStyle(null);

            attendancePane.setVisible(false);
            attendancePane.setVisible(false);
            classRatingPane.setVisible(false);
            studentsPane.setVisible(false);
            accountsPane.setVisible(false);

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
            accountsBtn.setStyle(null);

            classRatingPane.setVisible(false);
            studentsPane.setVisible(false);
            accountsPane.setVisible(false);

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
            accountsBtn.setStyle(null);

            studentsPane.setVisible(false);
            accountsPane.setVisible(false);

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
            accountsBtn.setStyle(null);

            accountsPane.setVisible(false);


            /*
            Integration
             */




        }
        else if (event.getSource() == accountsBtn){
            header.setVisible(true);
            dashboardPane.setVisible(false);
            attendancePane.setVisible(false);
            classRatingPane.setVisible(false);
            studentsPane.setVisible(false);
            accountsPane.setVisible(true);
            accountsPane.toFront();
            String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
            accountsBtn.setStyle(style);
            attendanceBtn.setStyle(null);
            classRatingBtn.setStyle(null);
            studentsBtn.setStyle(null);
            dashboardBtn.setStyle(null);

        }
        else if (event.getSource() == accountsBtn){
            header.setVisible(true);
            dashboardPane.setVisible(false);
            attendancePane.setVisible(false);
            classRatingPane.setVisible(false);
            studentsPane.setVisible(false);
            accountsPane.setVisible(true);
            accountsPane.toFront();
            String style = "-fx-background-color: #ffc13d; -fx-text-fill: #000000;";
            accountsBtn.setStyle(style);
            attendanceBtn.setStyle(null);
            classRatingBtn.setStyle(null);
            studentsBtn.setStyle(null);
            dashboardBtn.setStyle(null);
        }
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
        System.out.println(datePicker.getValue());
    }

    @FXML
    private void handleRadioButton(ActionEvent event){
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

    public void handleLogout(){
        try{
            app.changeSceneTo("login");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setAppInstance(App app){
        this.app = app;
    }

//
//    @FXML
//    private void handleComboBoxAction(ActionEvent event) {
//        if (modeComboBox.getValue().equals("Upload Records")){
//            forRecordsPane.toFront();
//            forRecordsPane.setVisible(true);
//            forRegistrationPane.setVisible(false);
//        }
//        else if (modeComboBox.getValue().equals("Upload File for Registration")){
//            forRegistrationPane.toFront();
//            forRecordsPane.setVisible(false);
//            forRegistrationPane.setVisible(true);
//        }
//    }
//
//    @FXML
//    private void chooseFile(MouseEvent event) {
//        FileChooser fc = new FileChooser();
//        fc.getExtensionFilters().addAll(
//        new FileChooser.ExtensionFilter("CSV Files", "*.pdf"));
////        File selectedFile = fc.showOpenDialog(null);
//        this.selectedFile = fc.showOpenDialog(null);
//        if (selectedFile != null){
//            recordsListview.getItems().add(selectedFile.getName());
//            registrationListview.getItems().add(selectedFile.getName());
//        }
//        else {
//            System.out.println("File is not valid.");
//        }
//    }
////
////    @FXML
////    private void handleUpload(MouseEvent event) {
////        System.out.println(selectedFile.getAbsolutePath());
////    }

}
