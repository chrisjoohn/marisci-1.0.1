package services;

import datasource.Database;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardServices {

    private Connection conn;
    private Database database = new Database();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public DashboardServices() {
        this.conn = database.getConn();
    }

    public int totalNumOfStudents(){

        String query = "SELECT COUNT(*) FROM students";
        int total = 0;

        try{
            Statement stmt = conn.createStatement();
            ResultSet res = (ResultSet) stmt.executeQuery(query);
            while(res.next()){
                total = res.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return total;
    }

    public int totalNumOfPresent(){

        String query = "SELECT SUM(isPresent) FROM attendance WHERE date=?";
        int total = 0;

        Date date = new Date();

        String today = dateFormat.format(date);

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, today);

            ResultSet res = prepSt.executeQuery();

            while(res.next()){
                total = res.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return total;
    }

    public int totalNumStudPerGradeLevel(int gradeLevel){

        String query = "SELECT COUNT(*) FROM students WHERE grade_level=?";
        int total = 0;

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, String.valueOf(gradeLevel));

            ResultSet res = prepSt.executeQuery();

            while(res.next()){
                total = res.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return total;
    }
}
