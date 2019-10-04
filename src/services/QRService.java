package services;

import datasource.Database;
import helper.QRcode;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class QRService {

    @FXML TextField firstName;
    @FXML TextField middleName;
    @FXML TextField lastName;

    private QRcode qrCode = new QRcode();
//    private Student student;
    private final String Path="QRs/";

    private StudentServices studentServices = new StudentServices();
    private Connection conn;
    private Database database = new Database();

    public QRService(){
        this.conn = database.getConn();
//        this.student=student;
    }


    public boolean GenerateQR(String first_name,
                              String middle_name,
                              String last_name){
        String LRN = studentServices.getLRN(first_name, middle_name, last_name);
        String filePath = Path+LRN+".png";

        String query = "UPDATE students SET QR_code=1 WHERE LRN=?";

        boolean success = false;

        if(!LRN.equals(null)){
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, LRN);
                int i = preparedStatement.executeUpdate();


                success = (qrCode.generateQRcode(LRN, filePath) && (i>0));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return success;
    }

    public boolean GenerateQR(String LRN){
        String filePath = Path+LRN+".png";
        String query = "UPDATE students SET QR_code=1 WHERE LRN=?";
        boolean success = false;

        if(!LRN.equals(null)){
            try {
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, LRN);
                int i = preparedStatement.executeUpdate();

                success = (qrCode.generateQRcode(LRN, filePath) && (i>0));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return success;
    }
}
