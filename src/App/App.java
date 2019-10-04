package App;

import controllers.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.ModelFactory;
import views.ViewFactory;

public class App extends Application {

    private ViewFactory viewFactory = new ViewFactory();
    private ModelFactory modelFactory = new ModelFactory();

    public Stage stage;


    @Override
    public void start(Stage stage){
        this.stage = stage;
        try {
            changeSceneTo("LandingPage");
//            changeSceneTo("TeacherDashboard");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        stage.setResizable(false);
        stage.show();

    }

    public void changeSceneTo(String view) throws Exception{

        FXMLLoader loader;
        Scene scene= null;

        switch (view) {

            /**
             * LANDING PAGE
             */
            case "LandingPage":
                loader = new FXMLLoader(viewFactory.Menu);
                MenuController menuController = new MenuController(modelFactory.user);
                menuController.setAppInstance(this);
                loader.setController(menuController);

                scene = new Scene(loader.load());

                break;

            /**
             * ATTENDANCE
             */

            case "ScanQR":
                loader = new FXMLLoader(viewFactory.ScanQR);
                ScanQRController scanQRController = new ScanQRController(modelFactory.student, modelFactory.attendance);
                scanQRController.setAppInstance(this);
                loader.setController(scanQRController);

                scene = new Scene(loader.load());
                break;

//            case "AttendanceMenu":
//                loader = new FXMLLoader(viewFactory.AttendanceMenu);
//                AttendanceMenuController attendanceMenuController = new AttendanceMenuController();
//                attendanceMenuController.setAppInstance(this);
//                loader.setController(attendanceMenuController);
//
//                scene = new Scene(loader.load());
//                break;

            /**
             * LOGIN
             */
            case "login":
                loader = new FXMLLoader(viewFactory.Login);
                LoginController loginController = new LoginController(modelFactory.user);
                loginController.setAppInstance(this);
                loader.setController(loginController);

                scene = new Scene(loader.load());
                break;


            /**
             * DASHBOARD
             */

            case "AdminDashboard":
                loader = new FXMLLoader(viewFactory.MindMappingAdmin);
                AdminDashboardController adminDashboardController = new AdminDashboardController(modelFactory.user);
                adminDashboardController.setAppInstance(this);
                loader.setController(adminDashboardController);

                scene = new Scene(loader.load());
                break;


            case "TeacherDashboard":
                loader = new FXMLLoader(viewFactory.MindMappingTeachers);
                TeacherDashboardController teacherDashboardController = new TeacherDashboardController(modelFactory.user);
                teacherDashboardController.setAppInstance(this);
                loader.setController(teacherDashboardController);

                scene = new Scene(loader.load());
                break;


            /**
             * USER REGISTRATION
             */

            case "UserRegistration":
                loader = new FXMLLoader(viewFactory.UserRegistration);
                UserRegistrationController userRegistrationController = new UserRegistrationController(modelFactory.user);
                userRegistrationController.setAppInstance(this);
                loader.setController(userRegistrationController);

                scene = new Scene(loader.load());
                break;

        }

        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }



}
