package views;

import java.net.URL;

public class ViewFactory {

    /**
     * LOGIN
     */
    public URL Login = getClass().getResource("Login.fxml");

    /**
     * LANDING PAGE
     */
    public URL Menu = getClass().getResource("LandingPage.fxml");


    /**
     * MIND MAPPING
     */

    public URL MindMappingAdmin = getClass().getResource("MindMappingAdmin.fxml");
    public URL MindMappingTeachers = getClass().getResource("MindMappingTeacher.fxml");


    /**
     * ATTENDANCE
     */

    public URL ScanQR = getClass().getResource("QRScan.fxml");
//    public URL AttendanceMenu = getClass().getResource("AttendanceMenu.fxml");
//    public URL TimeInAttendance = getClass().getResource("TimeInAttendance.fxml");
//    public URL TimeOutAttendance = getClass().getResource("TimeOutAttendance.fxml");

    /**
     * REGISTRATION
     */

    public URL UserRegistration = getClass().getResource("Registration.fxml");

}


