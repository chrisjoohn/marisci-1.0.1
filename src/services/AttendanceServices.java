package services;

import datasource.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AttendanceServices {
    private Connection conn;
    private Database database = new Database();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public AttendanceServices(){
        this.conn = database.getConn();
        InitializeAttendance();
    }

    public boolean InitializeAttendance(){
        boolean success = false;
        String query="INSERT INTO attendance(LRN, date) SELECT LRN, ? FROM students " +
                "WHERE NOT EXISTS(SELECT date FROM attendance WHERE date = ?)";

        Date date = new Date();
        String now = this.dateFormat.format(date);

        try {
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, now);
            prepSt.setString(2, now);

            int i = prepSt.executeUpdate();

            success = (i>0);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return success;
    }


    public boolean RecordTimeIn(String LRN){
        String query = "UPDATE attendance SET time_in = ?, isPresent=1 WHERE LRN=? AND date=?";
        Date date = new Date();

        String today = dateFormat.format(date);
        String time = timeFormat.format(date);

        boolean success = false;

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, time);
            prepSt.setString(2, LRN);
            prepSt.setString(3, today);

            int i = prepSt.executeUpdate();

            success = (i > 0);

        }catch (Exception e){
            e.printStackTrace();
        }

        return success;
    }


    public boolean RecordTimeOut(String LRN){
        boolean success = false;
        String query = "UPDATE attendance SET time_out = ?, status = 'present' WHERE LRN = ? AND date = ? AND time_in IS NOT NULL AND time_out IS NULL";

        Date date = new Date();

        String today = dateFormat.format(date);
        String time = timeFormat.format(date);
        try {
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, time);
            prepSt.setString(2, LRN);
            prepSt.setString(3, today);

            int i = prepSt.executeUpdate();

            success = (i > 0);

        }catch (Exception e){
            e.printStackTrace();
        }

        return success;
    }


    //GET ATTENDANCE - START

    public ArrayList<HashMap<String, String>> GetAttendance(){ //getting attendance for the current day
        ArrayList<HashMap<String, String>> attendance = new ArrayList<>();
        Date date = new Date();

        String today = dateFormat.format(date);
        String query = "SELECT LRN, time_in, time_out, status FROM attendance " +
                "WHERE date=?";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, today);

            ResultSet rs = prepSt.executeQuery();

            while (rs.next()){
                HashMap<String, String> data = new HashMap<>();
                data.put("LRN", rs.getString("LRN"));
                data.put("time_in", rs.getString("time_in"));
                data.put("time_out", rs.getString("time_out"));
                data.put("status", rs.getString("status"));

                attendance.add(data);

            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return attendance;
    }

    public ArrayList<HashMap<String, String>> GetAttendance(String grade_level){
        ArrayList<HashMap<String, String>> attendance = new ArrayList<>();

        String query = "SELECT students.first_name, students.last_name, time_in, time_out, status FROM attendance " +
                "INNER JOIN students ON students.LRN = attendance.LRN " +
                "WHERE students.grade_level = ? AND attendance.date = ?";

        Date date = new Date();
        String today = dateFormat.format(date);

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, grade_level);
            prepSt.setString(2, today);

            ResultSet rs = prepSt.executeQuery();

            while (rs.next()){
                HashMap<String, String> data  = new HashMap<>();
                data.put("name", rs.getString("first_name") +" "+rs.getString("last_name"));
                data.put("time_in", rs.getString("time_in"));
                data.put("time_out", rs.getString("time_out"));
                data.put("status", rs.getString("status"));

                attendance.add(data);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return attendance;
    }


    public ArrayList<HashMap<String, String>> GetAttendance(LocalDate date){
        ArrayList<HashMap<String, String>> attendance = new ArrayList<>();

        String query = "SELECT students.first_name, students.last_name, time_in, time_out, status FROM attendance " +
                "INNER JOIN students ON students.LRN = attendance.LRN " +
                "WHERE  AND attendance.date = ?";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, String.valueOf(date));

            ResultSet rs = prepSt.executeQuery();

            while (rs.next()){
                HashMap<String, String> data = new HashMap<>();

                data.put("name", rs.getString("first_name") +" "+rs.getString("last_name"));
                data.put("time_in", rs.getString("time_in"));
                data.put("time_out", rs.getString("time_out"));
                data.put("status", rs.getString("status"));

                attendance.add(data);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return attendance;
    }



    public ArrayList<HashMap<String, String>> GetAttendance(String LRN,String from, String to){
        ArrayList<HashMap<String, String>> attendance = new ArrayList<>();

        String query = "SELECT date, time_in, time_out, isPresent FROM attendance WHERE LRN=? AND date BETWEEN ? and ? SORT BY DATE";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, LRN);
            prepSt.setString(2, from);
            prepSt.setString(3, to);

            ResultSet rs = prepSt.executeQuery();

            while (rs.next()){
                HashMap<String, String> data = new HashMap<>();
                data.put("date", rs.getString("date"));
                data.put("time_in", rs.getString("time_in"));
                data.put("time_out", rs.getString("time_out"));
                data.put("isPresent", rs.getString("isPresent"));

                attendance.add(data);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return attendance;
    }



    //GET ATTENDANCE -END

    public boolean AddHolidaySuspension(String date, String status){
        boolean success = false;
        String query="INSERT INTO attendance(LRN, date,status) SELECT LRN, ?, ? FROM students " +
                "WHERE NOT EXISTS(SELECT date FROM attendance WHERE date = ?)";

        String now = this.dateFormat.format(date);

        try {
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, now);
            prepSt.setString(2, status);
            prepSt.setString(3, now);

            int i = prepSt.executeUpdate();

            success = (i>0);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return success;
    }


}
