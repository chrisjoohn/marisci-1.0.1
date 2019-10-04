package models;

import datasource.Database;
import services.AttendanceServices;

import java.util.ArrayList;
import java.util.HashMap;

public class Attendance {
    private String LRN;
    private String date;
    private String gradeLevel;
    private String section;
    private String timeIn;
    private String timeOut;

    private Database database = new Database();
    private AttendanceServices attendanceServices = new AttendanceServices();

    public void setLRN(String LRN) {
        this.LRN = LRN;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public boolean recordTimeIn(){
        return attendanceServices.RecordTimeIn(this.LRN);
    }

    public boolean recordTimeOut(){
        return attendanceServices.RecordTimeOut(this.LRN);
    }

    public boolean inDB(){
        return database.getAttendance(this.LRN);
    }

    public ArrayList<HashMap<String, String>> getAttendanceRecord(){
        return database.getAttendanceRecord(this.date, this.gradeLevel, this.section);
    }

//    public ArrayList<ArrayList<String>> getAttendanceOfStudent(String LRN){
//
//    }
}
