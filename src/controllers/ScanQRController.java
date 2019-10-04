package controllers;

import App.App;
import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Attendance;
import models.Student;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ScanQRController implements Initializable, Runnable {
    private App app;
    private Student student;
    private Attendance attendance;

    private String month, day, time;
    private Thread thread;
    private SimpleDateFormat format;
    private Date date;
    private Calendar calendar;

    private BufferedImage image;
    private Webcam webcam;
    private boolean cameraIsOpen = false;
    Result result;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
    private String read;

    @FXML private Label timeStamp;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private ImageView webCam;
    @FXML private TextField LRN;
    @FXML private TextField firstName;
    @FXML private TextField lastName;

    public void handleCancel(){
        cameraIsOpen=false;
    }

    public ScanQRController(Student student, Attendance attendance) {
        this.student = student;
        this.attendance = attendance;
    }

    private void record(String newValue){

        student.setLRN(newValue);
        attendance.setLRN(newValue);

        HashMap<String, String> data = student.retrieveStudent();

        if(data.size() > 0){
            if(!attendance.inDB()) {
                if(attendance.recordTimeIn()) {
                    firstName.setText(data.get("first_name"));
                    lastName.setText(data.get("last_name"));
                }else {
                    DisplayError("Error in recording Time in");
                }
            }else{
                DisplayError("Attendance Already recorded!");
            }
        }else{
            DisplayError("Student not found");
        }
    }

    private void DisplayError(String message){
        System.out.println(message);
    }

    public void setAppInstance(App app){
        this.app=app;
    }

    public void handleBack(){
        try{
            app.changeSceneTo("LandingPage");
        }catch (Exception e){
            e.printStackTrace();
        }
        disposeCamera();
    }


    @Override
    public void run() {
        try{
            while (true){
                calendar = Calendar.getInstance();

                format = new SimpleDateFormat("hh:mm:ss a");
                date = calendar.getTime();
                time = format.format(date);

                format = new SimpleDateFormat("MMMM dd yyyy");
                date = calendar.getTime();
                month = format.format(date);

                format = new SimpleDateFormat("EEEE");
                date = calendar.getTime();
                day = format.format(date);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        dateLabel.setText(String.valueOf(month) + ", " +day);
                        timeLabel.setText(time);
                        timeStamp.setText(time);
                    }
                });
                Thread.sleep(1000);
            }
        }catch (Exception e){

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        thread = new Thread(this);
        thread.start();
        initializeWebCam();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                setImageViewSize();
            }
        });


    }

    protected void initializeWebCam() {

        Task<Void> webCamIntializer = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                webcam = Webcam.getDefault();
                webcam.open();
                startWebCamStream();
                return null;
            }

        };

        new Thread(webCamIntializer).start();
    }

    protected void setImageViewSize() {

        webCam.setFitHeight(400);
        webCam.setFitWidth(400);
        webCam.prefHeight(400);
        webCam.prefWidth(400);
        webCam.setPreserveRatio(true);

    }

    protected void startWebCamStream() {

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (!cameraIsOpen) {

                    try {
                        if ((image = webcam.getImage()) != null) {
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    final Image mainiamge = SwingFXUtils
                                            .toFXImage(image, null);
                                    imageProperty.set(mainiamge);

                                    LuminanceSource source = new BufferedImageLuminanceSource(image);

                                    BinaryBitmap bitMap = new BinaryBitmap(new HybridBinarizer(source));

                                    try{
                                        result = new MultiFormatReader().decode(bitMap);
                                    } catch (Exception e){

                                    }

                                    if(result!=null){
                                        read=result.getText();
                                        if(!LRN.getText().equals(read)) {
                                            LRN.setText(read);
                                            record(read);
//                                            System.out.println(read);
                                        }

                                    }
                                }
                            });

                            image.flush();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }

        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        webCam.imageProperty().bind(imageProperty);

    }

    protected void disposeCamera(){
        cameraIsOpen = true;

        if(webcam != null){
            webcam.close();
        }

    }
}
