package main.java.edu.kit.cm.ivu.auth.qr;

import java.io.File;
import java.io.IOException;

import sun.misc.BASE64Encoder;

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


	public File generate(QrMessage message, int width) throws WriterException, IOException
	{
		File imageFile = File.createTempFile("qrcode", ".png", this.imagePath);
		
		// Need to use base64 encoding because otherwise non ascii bytes would be broken
		BASE64Encoder encoder = new BASE64Encoder();
		String content = encoder.encodeBuffer(message.content());
        Writer qrWriter = new QRCodeWriter();
        BitMatrix bb = qrWriter.encode(new String(content), BarcodeFormat.QR_CODE, width, width);
        MatrixToImageWriter.writeToFile(bb, "png", imageFile);

		return imageFile;
	}
	
	
	public File generate(QrMessage message) throws WriterException, IOException
	{
		return this.generate(message, 150);
	}
}
