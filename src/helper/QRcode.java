package helper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRcode {

    public boolean generateQRcode(String studentId, String filePath){
        boolean success = false;

        try{
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(studentId, BarcodeFormat.QR_CODE,350,350);

            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix,"PNG", path);

            success = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return success;
    }
}
