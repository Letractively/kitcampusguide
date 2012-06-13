package main.java.edu.kit.cm.auth;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.edu.kit.cm.auth.LoginHandler;
import main.java.edu.kit.cm.auth.qr.QrException;
import main.java.edu.kit.cm.auth.qr.QrGenerator;
import main.java.edu.kit.cm.auth.qr.QrLoginMessage;
import main.java.edu.kit.cm.auth.qr.QrMessage;
import main.java.edu.kit.cm.auth.qr.QrParser;
import main.java.edu.kit.cm.auth.qr.QrProvider;
import main.java.edu.kit.cm.auth.qr.QrRegisterMessage;

/**
 * Servlet implementation class QrLoginHandler
 */
public class QrLoginHandler extends LoginHandler {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
//		QrLoginMessage qrMessage = new QrLoginMessage(QrProvider.KCG);
//		qrMessage.setChallenge(hexStringToByteArray(UUID.randomUUID().toString().replace("-", "")));

		QrRegisterMessage qrMessage = new QrRegisterMessage(QrProvider.KCG);
		qrMessage.setResponseUrl("http://localhost:8080/AuthTester/qrauth");
		qrMessage.setUserId(new byte[] {0x47, 0x11});
		qrMessage.setSecret(hexStringToByteArray(UUID.randomUUID().toString().replace("-", "")));		
		
		QrGenerator qrGenerator = new QrGenerator(this.getQrImagePath());
		
		String qrCodeFileName = "invalid.png";
		
		try {
			qrCodeFileName = qrGenerator.generate(qrMessage);
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		request.setAttribute("qrCodeFileName", qrCodeFileName);
		request.getRequestDispatcher("/loginForm.jsp").forward(request, response);		

		try {
			QrMessage parsedMessage = QrParser.parse(qrMessage.content());
		} catch (QrException e) {
			System.err.println("QR exception: " + e.getMessage());
		}
	}
	
	
	protected String getQrImagePath()
	{
		return this.getServletContext().getRealPath("")
			   + File.separator + "images"
			   + File.separator  + "qrcodes"
			   + File.separator;
	}


	public static byte[] hexStringToByteArray(String s)
	{
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    
	    return data;
	}


	public static String byteArrayToHexString(byte[] b) {
	    StringBuffer sb = new StringBuffer(b.length * 2);
	    for (int i = 0; i < b.length; i++) {
	      int v = b[i] & 0xff;
	      if (v < 16) {
	        sb.append('0');
	      }
	      sb.append(Integer.toHexString(v));
	    }
	    return sb.toString();
	  }

}
