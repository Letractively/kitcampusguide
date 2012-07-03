package main.java.edu.kit.cm.ivu.auth;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.edu.kit.cm.ivu.auth.db.DeviceRepository;
import main.java.edu.kit.cm.ivu.auth.db.UserRepository;
import main.java.edu.kit.cm.ivu.auth.qr.QrGenerator;
import main.java.edu.kit.cm.ivu.auth.qr.QrProvider;
import main.java.edu.kit.cm.ivu.auth.qr.QrRegisterMessage;

/**
 * Servlet implementation class DeviceHandler
 */
@WebServlet("/devices")
public class DeviceHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeviceHandler() {
        super();
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

        QrRegisterMessage qrMessage = new QrRegisterMessage(QrProvider.KCG);
        qrMessage.setResponseUrl("http://i71vpc06.cm-tm.uni-karlsruhe.de:8080/AuthTester/responder");
        // TODO: Replace with UserID of current session after login
        qrMessage.setUserId(UserRepository.DUMMY_ID);
        // TODO: Replace with actual random generator as soon as secrets are (really) stored in DB
        qrMessage.setSecret(DeviceRepository.DUMMY_SECRET);

        QrGenerator qrGenerator = new QrGenerator(this.getQrImagePath());
        
        File qrCodeFile = null;
        
        try {
            qrCodeFile = qrGenerator.generate(qrMessage, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("qrCodeFile", qrCodeFile);
        request.getRequestDispatcher("/devices.jsp").forward(request, response);  
	}


    protected String getQrImagePath()
    {
        return this.getServletContext().getRealPath("")
               + File.separator + "img"
               + File.separator  + "qrcodes"
               + File.separator;
    }
}
