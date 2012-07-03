package main.java.edu.kit.cm.ivu.auth;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.edu.kit.cm.ivu.auth.db.Challenge;
import main.java.edu.kit.cm.ivu.auth.db.ChallengeRepository;
import main.java.edu.kit.cm.ivu.auth.qr.QrGenerator;
import main.java.edu.kit.cm.ivu.auth.qr.QrLoginMessage;
import main.java.edu.kit.cm.ivu.auth.qr.QrProvider;
import main.java.edu.kit.cm.ivu.auth.util.HexHelper;

/**
 * Servlet implementation class MonitorHandler
 */
@WebServlet("/monitor")
public class MonitorHandler extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    
    /**
     * @see MonitorHandler#HttpServlet()
     */
    public MonitorHandler() {
        super();
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	String challenge = UUID.randomUUID().toString().replace("-", "");
        QrLoginMessage qrMessage = new QrLoginMessage(QrProvider.KCG);

        qrMessage.setChallenge(HexHelper.hexStringToByteArray(challenge));

        QrGenerator qrGenerator = new QrGenerator(this.getQrImagePath());
        
        File qrCodeFile = null;
        
        try {
            qrCodeFile = qrGenerator.generate(qrMessage);
            this.storeChallenge(request.getSession(), challenge);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("qrCodeFile", qrCodeFile);
        request.getRequestDispatcher("/monitor.jsp").forward(request, response);
        
        System.out.println("Generated challenge: " + challenge);
        System.out.println("Generated QR message: " + HexHelper.byteArrayToHexString(qrMessage.content()));
    }
    
    
    protected String getQrImagePath()
    {
        return this.getServletContext().getRealPath("")
               + File.separator + "img"
               + File.separator  + "qrcodes"
               + File.separator;
    }
    
    
    private void storeChallenge(HttpSession session, String challenge)
    {
    	ChallengeRepository rep = new ChallengeRepository(this);
    	rep.add(new Challenge(challenge, session.getId()));
    	rep.close();
    }

}
