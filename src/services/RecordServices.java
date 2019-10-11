package services;

import datasource.Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class RecordServices {

    private Connection conn;
    private Database database = new Database();

    public RecordServices(){
        this.conn = database.getConn();
    }

    private ArrayList<HashMap<String, String>> readCSV(String csvPath){
        ArrayList<HashMap<String, String>> datas = new ArrayList<>();
        String csvFile = csvPath;
        String byComma = ",";
        String line = "";
        String[] headers = {"LRN", "score"};

        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            boolean skip = true;
            while((line = br.readLine()) != null){
                if(skip){
                    skip=false;
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

    public boolean CreateRecordCSV(String grading_period,
                                   String quiz_number,
                                   String subject,
                                   String csvPath){
        boolean success = false;
        String query = "INSERT INTO records(LRN, grading_period, quiz_number, score, subject) " +
                "VALUES(?,?,?,?,?)";
        ArrayList<HashMap<String, String>> CSVInput = readCSV(csvPath);

        for(HashMap<String, String> data: CSVInput){
            try{
                PreparedStatement prepSt = conn.prepareStatement(query);

                prepSt.setString(1, data.get("LRN"));
                prepSt.setString(2, grading_period);
                prepSt.setString(3, quiz_number);
                prepSt.setString(4, data.get("score"));
                prepSt.setString(5, subject);

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

}
