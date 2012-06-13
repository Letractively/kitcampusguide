package main.java.edu.kit.cm.auth.qr;

import java.io.File;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrGenerator
{
	private String imagePath;

	
	public QrGenerator(String imagePath)
	{
		this.imagePath = imagePath;
	}


	public String generate(QrMessage message) throws WriterException, IOException
	{
		String imageFileName = "qr.png";

        Writer qrWriter = new QRCodeWriter();

        BitMatrix bb = qrWriter.encode(new String(message.content()), BarcodeFormat.QR_CODE, 300, 300);
        MatrixToImageWriter.writeToFile(bb, "png", new File(this.imagePath + imageFileName));

		return imageFileName;
	}
}
