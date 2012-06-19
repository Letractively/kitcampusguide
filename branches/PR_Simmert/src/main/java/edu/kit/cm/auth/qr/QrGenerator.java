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
	private File imagePath;

	
	public QrGenerator(String imagePath)
	{
		this.imagePath = new File(imagePath);
	}


	public File generate(QrMessage message) throws WriterException, IOException
	{
		File imageFile = File.createTempFile("qrcode", ".png", this.imagePath);

        Writer qrWriter = new QRCodeWriter();

        BitMatrix bb = qrWriter.encode(new String(message.content()), BarcodeFormat.QR_CODE, 150, 150);
        MatrixToImageWriter.writeToFile(bb, "png", imageFile);

		return imageFile;
	}
}
