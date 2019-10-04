package datasource;

import config.DBConf;
import helper.Encryption;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Database {

    private DBConf dbConf = new DBConf();
    private Encryption encryption;

    private final String url = dbConf.URL;
    private final String user = dbConf.USER;
    private final String password = dbConf.PASSWORD;
    private final String driver = dbConf.DRIVER;

    private Connection conn;

    public Database(){
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            this.conn = conn;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Database Connection");
        }


        try {
            encryption = new Encryption();
        }catch (Exception e){

        }
    }

    public Connection getConn(){
        return this.conn;
    }

    //-----------------------------TEACHER------------------------------------------------//

    public boolean createTeacher(String firstName,
                              String lastName,
                              String employeeId,
                              String position,
                              String birthday,
                              String username,
                              String email,
                              String passwordInput){

        String password = encryption.hashPassword(passwordInput);

        String query = "INSERT INTO accounts(first_name, last_name, employee_id, position, birthday, username, email, password)" +
                "VALUES (?,?,?,?,?,?,?,?)";

        boolean action=false;

        try {

            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, firstName);
            prepSt.setString(2, lastName);
            prepSt.setString(3, employeeId);
            prepSt.setString(4, position);
            prepSt.setString(5, birthday);
            prepSt.setString(6, username);
            prepSt.setString(7, email);
            prepSt.setString(8, password);

            int i = prepSt.executeUpdate();

            action = (i > 0);


        }catch (Exception e){

            e.printStackTrace();
            System.out.println("Error in createTeacher()");
        }

        return action;

    }
//
//    public ArrayList<String> getTeacher(String username, String password){
//
//        ArrayList<String> data = new ArrayList<>();
//        String query = "SELECT first_name, last_name FROM accounts WHERE username LIKE ? AND password LIKE ?";
//        try {
//            PreparedStatement prepSt = conn.prepareStatement(query);
//            prepSt.setString(1, username);
//            prepSt.setString(2, encryption.hashPassword(password));
//            ResultSet resultSet = prepSt.executeQuery();
//
//            while (resultSet.next()) {
//                data.add(resultSet.getString("first_name"));
//                data.add(resultSet.getString("last_name"));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.err.println("Error in getTeacher()");
//        }
//
//        return data;
//    }


    public boolean updateTeacherPassword(String employeeId, String newPassword){
        String query = "UPDATE accounts SET password = ? WHERE employee_id = ?";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, newPassword);
            prepSt.setString(2, employeeId);

            prepSt.execute();

            return true;

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in updateTeacherPassword()");
        }
        return false;
    }

    public boolean updateTeacherFirstName(String employeeId, String firstName){
        String query = "UPDATE accounts SET first_name = ? WHERE employee_id = ?";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, firstName);
            prepSt.setString(2, employeeId);

            return prepSt.execute();

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateTeacherLastName(String employeeId, String lastName){
        String query = "UPDATE accounts SET first_name = ? WHERE employee_id = ?";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, lastName);
            prepSt.setString(2, employeeId);

            return prepSt.execute();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in updateTeacherLastName()");
        }

        return false;
    }

    public boolean updatePosition(String employeeId, String position){

        String query = "UPDATE accounts SET position = ? WHERE employee_id = ?";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, position);
            prepSt.setString(2, employeeId);

            return prepSt.execute();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in updatePosition()");

        }

        return false;
    }

    //-----------------------------STUDENT------------------------------------------------//


    public boolean addStudentToDB(String firstName,
                                  String lastName,
                                  String LRN,
                                  String birthday,
                                  String section,
                                  String gradeLevel,
                                  String username,
                                  String email,
                                  String password){
        String query = "INSERT INTO students(first_name, last_name, LRN, birthday, section, grade_level, username, email, password) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1,firstName);
            prepSt.setString(2,lastName);
            prepSt.setString(3, LRN);
            prepSt.setString(4,birthday);
            prepSt.setString(5,section);
            prepSt.setString(6, gradeLevel);
            prepSt.setString(7, email);


            prepSt.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error in addStudentToDB()");
        }
        return false;
    }

    public ArrayList<String> retrieveStudent(String LRN){
        String query="SELECT * FROM students WHERE LRN LIKE ?";
        ArrayList<String> student = new ArrayList<>();
        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, LRN);

            ResultSet rs = prepSt.executeQuery();
            while(rs.next()){
                student.add(rs.getString("first_name"));
                student.add(rs.getString("middle_name"));
                student.add(rs.getString("last_name"));
                student.add(rs.getString("LRN"));
                student.add(rs.getString("age"));
                student.add(rs.getString("section"));
                student.add(rs.getString("grade_level"));
                student.add(rs.getString("email"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in retrieveStudent()");
        }
        return student;
    }

    public ArrayList<ArrayList<String>> retrieveAllStudents(){
        String query = "SELECT * FROM students";
        ArrayList<ArrayList<String>> datas = new ArrayList<ArrayList<String>>();

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            ResultSet rs = prepSt.executeQuery();
            while(rs.next()){
                ArrayList<String> data = new ArrayList<String>();
                data.add(rs.getString("first_name"));
                data.add(rs.getString("middle_name"));
                data.add(rs.getString("last_name"));
                data.add(rs.getString("LRN"));
                data.add(rs.getString("age"));
                data.add(rs.getString("section"));
                data.add(rs.getString("grade_level"));
                data.add(rs.getString("email"));

                datas.add(data);
            }
            return datas;

        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("Error in retrieveAllStudents()");
        }
        return datas;
    }

    public String getLRN(String firstName, String middleName, String lastName){
        String query = "SELECT studentId FROM students WHERE first_name LIKE ? AND middle_name LIKE ? AND last_name LIKE ? ";
        String LRN = null;
        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, firstName);
            prepSt.setString(2, middleName);
            prepSt.setString(3, lastName);

            ResultSet rs = prepSt.executeQuery();

            while(rs.next()){
                LRN = rs.getString(1);
            }
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Error in getLRN()");
        }
        return LRN;
    }

    //GET COUNT OF STUDENTS PER GRADE LEVEL
    public int numOfStudentPerGradeLevel(String gradeLevel){
        String query = "SELECT COUNT(*) FROM students WHERE grade_level = ?";
        int count=0;
        try {
            PreparedStatement prepSt = conn.prepareStatement(query);

            ResultSet rs = prepSt.executeQuery();
            if(rs.next()){
                count = Integer.valueOf(rs.getString("COUNT(*)"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in numOfStudentPerGradeLevel()");
        }

        return count;
    }

    //GET TOTAL NUMBER OF STUDENTS;
    public int getCountOfStudents(){
        String query = "SELECT COUNT(*) FROM students";
        int count=0;
        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            ResultSet rs = prepSt.executeQuery();

            if(rs.next()){
                count=Integer.valueOf(rs.getString("COUNT(*)"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e);
        }
        return count;
    }
    //-----------------------------RECORD------------------------------------------------//


    //CREATE
    public boolean createRecord(String teacherId,
                                String subject,
                                String section,
                                String grade,
                                int quizNum,
                                ArrayList<ArrayList<String>> list){

        String query = "INSERT INTO records";


        for(ArrayList<String> students: list) {
            try{
                PreparedStatement prepSt = conn.prepareStatement(query);

                prepSt.setString(1, teacherId);
                prepSt.setString(2, subject);
                prepSt.setString(3, section);
                prepSt.setString(4, grade);
                prepSt.setInt(5, quizNum);
                prepSt.setString(6, students.get(0));
                prepSt.setString(7, students.get(1));
                prepSt.setString(8, students.get(2));

                prepSt.execute();

                return true;

            }catch (Exception e){
                e.printStackTrace();
                System.out.println("Error in createRecord()");
            }
        }
        return  false;
    }

    public ArrayList<String> getRecordPerSection(String section, String gradeLevel, int quizNum, String subject){
        ArrayList<String> data = new ArrayList<>();

        String query = "SELECT num_of_correct, num_of_wrong, total_items, total_students, subject" +
                "FROM Record WHERE section=?, grade_level=?, quiz_num=?, subject=?";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, section);
            prepSt.setString(2, gradeLevel);
            prepSt.setInt(3,  quizNum);
            prepSt.setString(4, subject);

            ResultSet resultSet = prepSt.executeQuery();

            while(resultSet.next()){
                data.add("num_of_correct");
                data.add("num_of_wrong");
                data.add("total_items");
                data.add("total_student");
                data.add("subject");
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in getRecordPerSection()");
        }

        return data;
    }

    public ArrayList<String> getDistinctSubjects(){ //returns all the subjects that has a record;
        ArrayList<String> data = new ArrayList<>();

        String query = "SELECT DISTINCT subject FROM Record";

        try{
            PreparedStatement preparedSt = conn.prepareStatement(query);

            ResultSet resultSet = preparedSt.executeQuery();

            while(resultSet.next()){
                data.add(resultSet.getString("subject"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in getDistinctSubjects()");
        }

        return data;
    }

    public ArrayList<String> getDistinctGradeLevel(){
        String query = "SELECT DISTINCT grade_level FROM Record";
        ArrayList<String> data = new ArrayList<>();

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            ResultSet resultSet = prepSt.executeQuery();

            while(resultSet.next()){
                data.add(resultSet.getString("grade_level"));
            }


        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in getDistinctGradeLevel()");
        }

        return data;
    }

    public ArrayList<String> getDistinctSection(String gradeLevel){
        ArrayList<String> data = new ArrayList<>();
        String query = "SELECT DISTINCT section FROM Record WHERE grade_level = ?";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, gradeLevel);

            ResultSet resultSet = prepSt.executeQuery();
            while(resultSet.next()){
                data.add("section");
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in getDistinctSection()");
        }

        return data;

    }


    public ArrayList<String> getRecordPerTeacher(){
        ArrayList<String> data = new ArrayList<>();

        String query = "SELECT num_of_correct, num_of_wrong, total_items, _tot";

        return data;
    }


    //--------------------------END RECORD-------------------------------------------//

    //-----------------------------ATTENDANCE------------------------------------//

//    public void recordAttendance(String LRN){
//        SimpleDateFormat date_formatter = new SimpleDateFormat("dd/MM/yyyy");
//        SimpleDateFormat time_formatter = new SimpleDateFormat("hh:mm a");
//        java.util.Date date = new java.util.Date();
//        String dates = date_formatter.format(date);
//        String times = time_formatter.format(date);
//
//        String query = "INSERT INTO attendance(studentId, date_recorded, time_recorded) VALUES(?,?,?)";
//
//        try{
//            PreparedStatement prepSt = conn.prepareStatement(query);
//
//            prepSt.setString(1, LRN);
//            prepSt.setString(2, dates);
//            prepSt.setString(3, times);
//
//            prepSt.execute();
//
//        }catch (SQLException e){
//            e.printStackTrace();
//            System.out.println("Error in recordAttedance()");
//        }
//
//    }


    //inserting all from students to attendance
    public boolean setLRNandAttendance(){
        String query="INSERT INTO attendance(LRN, date) SELECT LRN, ? FROM students " +
                "WHERE NOT EXISTS(SELECT date FROM attendance WHERE date = ?)";

        SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String now = date_formatter.format(date);

        boolean success = false;
        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, now);
            prepSt.setString(2, now);

            int i = prepSt.executeUpdate();

            success = (i > 0);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in setLRNAttendance()");
        }

        return success;
    }
//
//    public boolean setDateAttendance(){
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        String now = dateFormat.format(date);
//        String query = "UPDATE attendance SET date=? WHERE date = 0000-00-00";
//
//        try{
//
//            PreparedStatement prepSt = conn.prepareStatement(query);
//
//            prepSt.setString(1,now);
//            return prepSt.execute();
//
//
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Error in setDateAttendance()");
//        }
//
//        return false;
//    }

    /*
    CHECK IF THE STUDENT ALREADY HAVE time_in record for the day
     */
    public boolean getAttendance(String LRN){
        SimpleDateFormat date_formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String dates = date_formatter.format(date);

        String query = "SELECT * FROM attendance WHERE date = ? AND LRN = ? LIMIT 1";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, dates);
            prepSt.setString(2, LRN);

            ResultSet rs = prepSt.executeQuery();

            if(rs.next()){
                return true;
            }

        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Error in recordedAttendance()");
        }

        return false;
    }


    /*
    RECORD STUDENT TIME IN
     */
    public boolean recordTimeIn(String LRN){
        SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time_formatter = new SimpleDateFormat("hh:mm a");
        Date date = new Date();
        String dates = date_formatter.format(date);
        String time = time_formatter.format(date);

        String query = "UPDATE attendance SET time_in=?, is_present=1 WHERE LRN=? AND date=?";

        try{
            PreparedStatement prepst = conn.prepareStatement(query);

            prepst.setString(1, time);
            prepst.setString(2, LRN);
            prepst.setString(3, dates);

            return prepst.execute();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in recordTimeIn()");
        }
        return false;
    }

    /*
    RECORD STUDENT TIME OT
     */

    public boolean recordTimeOut(String LRN){
        SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time_formatter = new SimpleDateFormat("hh:mm a");
        Date date = new Date();
        String dates = date_formatter.format(date);
        String time = time_formatter.format(date);

        String query = "UPDATE attendance SET time_out = ? WHERE LRN = ? AND date = ?";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, time);
            prepSt.setString(2, LRN);
            prepSt.setString(3, dates);

            return prepSt.execute();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in recordTimeOut()");
        }

        return false;
    }


    /*
    GET ATTENDANCE OF A SECTION FOR A SPECIFIC DATE
     */
    public ArrayList<HashMap<String,String>> getAttendanceRecord(String date, String section, String gradeLevel){

        ArrayList<HashMap<String, String>> attendanceRecord = new ArrayList<HashMap<String, String>>();


        String query = "SELECT students.first_name, students.last_name, attendance.time_in, attendance.time_out, attendance.isPresent " +
                "FROM students INNER JOIN attendance ON students.LRN = attendance.LRN" +
                "WHERE attendance.date = ? AND students.section = ? AND students.gradeLevel = ?";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, date);
            prepSt.setString(2, section);
            prepSt.setString(3, gradeLevel);

            ResultSet rs = prepSt.executeQuery();

            while(rs.next()){
                HashMap<String, String> data = new HashMap<>();

                data.put("first_name", rs.getString("first_name"));
                data.put("last_name", rs.getString("last_name"));
                data.put("isPresent", rs.getString("isPresent"));
                data.put("time_in", rs.getString("time_in"));
                data.put("time_out", rs.getString("time_out"));

                attendanceRecord.add(data);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in getAttendanceRecord()");
        }
        return attendanceRecord;
    }


    /*
    GET ATTENDANCE OF A STUDENT WITHIN A DATE RANGE
     */

    public ArrayList<ArrayList<String>> getAttendanceOfStudent(String LRN, String From, String To){

        ArrayList<ArrayList<String>> attendanceRecord = new ArrayList<ArrayList<String>>();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String from = formatter.format(From);
        String to = formatter.format(To);

        String query="SELECT LRN, date FROM STUDENTS WHERE LRN = ? AND date BETWEEN ? and ? SORT BY date";

        try {
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, LRN);
            prepSt.setString(2, from);
            prepSt.setString(3, to);

            ResultSet rs = prepSt.executeQuery();

            while (rs.next()){
                ArrayList<String> data = new ArrayList<>();

                data.add(rs.getString("LRN"));
                data.add(rs.getString("date"));

                attendanceRecord.add(data);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in getAttendanceOfStudent()");
        }
        return attendanceRecord;
    }

    //-----------------------END ATTENDANCE-----------------------------------------//
    //---------------------------SCHEDULE------------------------------------------//

    public boolean createSchdule(String employeeId,
                                 String section,
                                 String gradeLevel,
                                 String time,
                                 String day,
                                 String subject
                                 ){

        String query = "INSERT INTO schedule (teacher_id, section, grade_level, time, day, subject)" +
                "VALUES (?,?,?,?,?,?)";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            return prepSt.execute();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in createSchedule");
        }
        return false;
    }

}
