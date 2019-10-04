package services;

import datasource.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class StudentServices {

    private Connection conn;

    private Database database = new Database();

    public StudentServices(){
        this.conn = database.getConn();
    }

    private ArrayList<HashMap<String, String>> StudentList(String grade_level, String section){
        String query = "SELECT first_name, last_name, LRN FROM students WHERE grade_level=? AND section=?";

        ArrayList<HashMap<String, String>> StudentList = new ArrayList<>();

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, grade_level);
            prepSt.setString(2, section);

            ResultSet rs = prepSt.executeQuery();

            while(rs.next()){
                HashMap<String, String> data = new HashMap<>();

                data.put("LRN", rs.getString("LRN"));
                data.put("first_name", rs.getString("first_name"));
                data.put("last_name", rs.getString("last_name"));

                StudentList.add(data);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return StudentList;

    }

    private ArrayList<String> NumOfQuiz(String grading_period, String subject){

        String query = "SELECT DISTINCT quiz_number FROM records WHERE grading_period=? AND subject=?";
        ArrayList<String> data = new ArrayList<>();

        try{
            PreparedStatement preparedSt = conn.prepareStatement(query);
            preparedSt.setString(1, grading_period);
            preparedSt.setString(2, subject);

            ResultSet rs = preparedSt.executeQuery();

            while (rs.next()){
                data.add(rs.getString("quiz_number"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return data;
    }


    public HashMap<Integer,ArrayList<HashMap<String, String>>> getStudentRecords(String grade_level,
                                                                                 String section,
                                                                                 String subject){

        HashMap<Integer, ArrayList<HashMap<String, String>>> records = new HashMap<>();

        ArrayList<HashMap<String, String>> StudentList = StudentList(grade_level, section);

        String query = "SELECT score FROM records WHERE grading_period=? AND quiz_number=? AND subject=? AND LRN=? LIMIT 1";
        int grading = 4;
        for(int i=1; i<=grading;i++){
            ArrayList<HashMap<String, String>> data = new ArrayList<>();

            //get number of quizzes per grading
            ArrayList<String> NumOfQuiz = NumOfQuiz(String.valueOf(i), subject);

            for (HashMap<String, String> student: StudentList){
                HashMap<String, String> record = new HashMap<>();

                record.put("first_name", student.get("first_name"));
                record.put("last_name", student.get("last_name"));
                record.put("quiz_count", String.valueOf(NumOfQuiz.size()));

                for(int j=0; j<NumOfQuiz.size(); j++){
                    try {
                        PreparedStatement prepSt = conn.prepareStatement(query);
                        prepSt.setString(1, String.valueOf(i));
                        prepSt.setString(2, NumOfQuiz.get(j));
                        prepSt.setString(3, subject);
                        prepSt.setString(4, student.get("LRN"));

                        ResultSet rs = prepSt.executeQuery();

                        if(rs.next()){
                            record.put("scoreOfQuiz_"+NumOfQuiz.get(j), rs.getString("score"));
                        }else{
                            record.put("scoreOfQuiz_"+NumOfQuiz.get(j), "");
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
                data.add(record);
            }
            records.put(i, data);
        }

        return records;
    }

    public ArrayList<HashMap<String, String>> GetStudentRecords(String grade_level,
                                                                String section,
                                                                String subject,
                                                                String grading_period){

        ArrayList<HashMap<String, String>> records = new ArrayList<>();

        //get number of quizzes in a specific grading_period
        ArrayList<String> NumOfQuiz = NumOfQuiz(grading_period, subject);
        ArrayList<HashMap<String, String>> StudentList = StudentList(grade_level, section);

        String query = "SELECT score FROM records WHERE grading_period=? AND quiz_number=? AND subject=? AND LRN=? LIMIT 1";

        for (HashMap<String, String> student: StudentList){
            HashMap<String, String> record = new HashMap<>();

            record.put("first_name", student.get("first_name"));
            record.put("last_name", student.get("last_name"));
            record.put("quiz_count", String.valueOf(NumOfQuiz.size()));

            for (int i=0; i<NumOfQuiz.size(); i++){
                try{
                    PreparedStatement prepSt = conn.prepareStatement(query);

                    prepSt.setString(1, grading_period);
                    prepSt.setString(2, NumOfQuiz.get(i));
                    prepSt.setString(3, subject);
                    prepSt.setString(4, student.get("LRN"));

                    ResultSet rs = prepSt.executeQuery();

                    if(rs.next()){
                        record.put("scoreOfQuiz_"+NumOfQuiz.get(i), rs.getString("score"));
                    }else{
                        record.put("scoreOfQuiz_"+NumOfQuiz.get(i), "");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            records.add(record);
        }

        return records;
    }

    private ArrayList<HashMap<String, String>> readCSV(String csvPath){

        ArrayList<HashMap<String, String>> datas = new ArrayList<>();

        String csvFile = csvPath;
        String byComma = ",";
        String line = "";
        String[] headers = {"LRN", "first_name", "middle_name", "last_name", "birthday", "section", "grade_level", "email"};

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            boolean skip =true; //skip the header

            while ((line = br.readLine()) != null){
                if(skip){
                    skip = false;
                    continue;
                }

                HashMap<String, String> data = new HashMap<>();

                String[] items = line.split(byComma);

                for(int i=0; i<headers.length; i++){
                    data.put(headers[i], items[i].trim());
                }
                datas.add(data);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return datas;
    }


    public boolean addMultipleStudent(String csvPath){
        boolean success = false;
        ArrayList<HashMap<String, String>> students = readCSV(csvPath);

        String query = "INSERT INTO students(first_name, middle_name, last_name, LRN, birthday, section, grade_level, email) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        for(HashMap<String, String> student: students){
            try{
                PreparedStatement prepSt = conn.prepareStatement(query);
                prepSt.setString(1, student.get("first_name"));
                prepSt.setString(2, student.get("middle_name"));
                prepSt.setString(3, student.get("last_name"));
                prepSt.setString(4, student.get("LRN"));
                prepSt.setString(5, student.get("birthday"));
                prepSt.setString(6, student.get("section"));
                prepSt.setString(7, student.get("grade_level"));
                prepSt.setString(8, student.get("email"));

                int i = prepSt.executeUpdate();
                success = (i > 0);
            }catch (SQLException e){
                e.printStackTrace();
                success = false;
                break;
            }
        }

        return success;
    }

    public String getLRN(String firstName, String middleName, String lastName){
        String query = "SELECT LRN FROM students WHERE first_name LIKE ? AND middle_name LIKE ? AND last_name LIKE ? LIMIT 1";
        String LRN = null;
        try{
            PreparedStatement prepSt = conn.prepareStatement(query);

            prepSt.setString(1, firstName);
            prepSt.setString(2, middleName);
            prepSt.setString(3, lastName);

            ResultSet rs = prepSt.executeQuery();

            if(rs.next()){
                LRN = rs.getString(1);
            }
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Error in getLRN()");
        }
        return LRN;
    }


    public ArrayList<String> GetStudentsWithoutQR(){

        ArrayList<String> students = new ArrayList<>();

        String query = "SELECT LRN FROM students WHERE QR_code=0";

        try {
            PreparedStatement prepSt = conn.prepareStatement(query);

            ResultSet rs = prepSt.executeQuery();

            while (rs.next()){
                students.add(rs.getString("LRN"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return students;
    }

    public HashMap<String, String> GetStudent(String LRN){
        String query="SELECT * FROM students WHERE LRN=? LIMIT 1";
        HashMap<String, String> student = new HashMap<>();
        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            prepSt.setString(1, LRN);

            ResultSet rs = prepSt.executeQuery();
            while(rs.next()){
                student.put("first_name",rs.getString("first_name"));
                student.put("middle_name",rs.getString("middle_name"));
                student.put("last_name", rs.getString("last_name"));
                student.put("LRN",rs.getString("LRN"));
//                student.put("age", rs.getString("age"));
                student.put("section",rs.getString("section"));
                student.put("grade_level",rs.getString("grade_level"));
                student.put("email", rs.getString("email"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error in retrieveStudent()");
        }
        return student;
    }
//
//    public ObservableList<ObservableList<String>> GetStudents(){
//
//        ObservableList<ObservableList<String>> students = FXCollections.observableArrayList();
//
//        String query = "SELECT first_name, middle_name, last_name, LRN, birthday, email FROM students";
//
//        try{
//            PreparedStatement prepSt = conn.prepareStatement(query);
//            ResultSet rs = prepSt.executeQuery();
//
//            while (rs.next()){
//                ObservableList<String> data = FXCollections.observableArrayList();
//
//                data.add(rs.getString("first_name"));
//                data.add(rs.getString("middle_name"));
//                data.add(rs.getString("last_name"));
//                data.add(rs.getString("LRN"));
//                data.add(rs.getString("birthday"));
//                data.add(rs.getString("email"));
//
//                students.add(data);
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return students;
//    }

    /**
     * FOR students table
     */
//    public ObservableList<ObservableList<String>> GetStudents(){
//
//        ObservableList<ObservableList<String>> students = FXCollections.observableArrayList();
//
//        String query = "SELECT first_name, middle_name, last_name, LRN, birthday, email FROM students";
//
//        try{
//            PreparedStatement prepSt = conn.prepareStatement(query);
//            ResultSet rs = prepSt.executeQuery();
//
//            while (rs.next()){
//                ObservableList<String> data = FXCollections.observableArrayList();
//                data.add(rs.getString("first_name"));
//                data.add(rs.getString("middle_name"));
//                data.add(rs.getString("last_name"));
//                data.add(rs.getString("LRN"));
//                data.add(rs.getString("birthday"));
//                data.add(rs.getString("email"));
//
//                students.add(data);
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return students;
//    }

    public ObservableList<Student> GetStudents(){
        ObservableList<Student> students = FXCollections.observableArrayList();

        String query = "SELECT first_name, middle_name, last_name, LRN, birthday, email FROM students";

        try{
            PreparedStatement prepSt = conn.prepareStatement(query);
            ResultSet rs = prepSt.executeQuery();

            while (rs.next()){

                students.add(new Student(
                    rs.getString("first_name"),
                    rs.getString("middle_name"),
                    rs.getString("last_name"),
                    rs.getString("LRN"),
                    rs.getString("birthday"),
                    rs.getString("email")
                ));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

}
