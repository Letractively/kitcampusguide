package main.java.edu.kit.cm.ivu.auth;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import main.java.edu.kit.cm.ivu.auth.db.Challenge;
import main.java.edu.kit.cm.ivu.auth.db.ChallengeRepository;
import main.java.edu.kit.cm.ivu.auth.db.Device;
import main.java.edu.kit.cm.ivu.auth.db.DeviceRepository;
import main.java.edu.kit.cm.ivu.auth.qr.QrAction;
import main.java.edu.kit.cm.ivu.auth.util.CryptoHelper;
import main.java.edu.kit.cm.ivu.auth.util.HexHelper;

/**
 * Servlet implementation class ResponseHandler
 */
@WebServlet("/reponse")
public class ResponseHandler extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse response;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResponseHandler() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.handleResponse(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.handleResponse(request, response);
	}
	
	
	private void handleResponse(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		QrAction action;
		Integer userId;

		this.response = response;

		try {
			action = this.findAction(Byte.parseByte(request.getParameter("action")));
		} catch (Exception e) {
			action = null;
		}
		
		if (action == null) {
			this.sendError("Invalid action");
			return;
		}
		
		try {
			userId = Integer.parseInt(request.getParameter("userId"));
		} catch (Exception e) {
			this.sendError("Invalid userId");
			return;
		}

		switch (action) {
		case REGISTER:
			this.handleRegistration(
				request,
				userId,
				request.getParameter("deviceId"),
				request.getParameter("deviceName")
			);
			break;
		case LOGIN:
			this.handleLogin(
				request,
				request.getParameter("deviceId"),
				userId,
				request.getParameter("challenge"),
				request.getParameter("challengeHash")
			);
			break;
		default:
			this.sendError("Action not supported");
		}

	}

	
	private void handleRegistration(HttpServletRequest request, Integer userId, String deviceId, String deviceName)
	{
		System.out.println("Incoming user registration");
		System.out.println("userId: " + userId);
		System.out.println("deviceId: " + deviceId);
		System.out.println("deviceName: " + deviceName);
	}


	private void handleLogin(HttpServletRequest request, String deviceId, int userId, String challengeId, String challengeHash) throws IOException
	{
		System.out.println("Incoming user login");
		System.out.println("deviceId: " + deviceId);
		System.out.println("userId: " + userId);
		System.out.println("challengeId: " + challengeId);
		System.out.println("challengeHash: " + challengeHash);

    	ChallengeRepository rep = new ChallengeRepository(this);
    	Challenge challenge = rep.findById(challengeId);
    	rep.close();
    	
    	if (challenge == null) {
			this.sendError("Invalid challenge: " + challengeId);
			return;
    	}
    	
    	HttpSession session = this.findSessionById(challenge.sessionId());
    	
    	if (session == null) {
			this.sendError("No corresponding session found.");
			return;
    	}
    	
    	if (this.loginIsValid(deviceId, userId, HexHelper.hexStringToByteArray(challengeId), HexHelper.hexStringToByteArray(challengeHash))) {
    		session.setAttribute("userId", userId);
    	} else {
			this.sendError("Invalid challenge hash.");
			return;
    	}
	}
	
	
	private boolean loginIsValid(String deviceId, int userId, byte[] challengeId, byte[] challengeHash)
	{
		Device device = DeviceRepository.findByIdAndOwner(deviceId, userId);
		byte[] hmac = CryptoHelper.hmacSha1(challengeId, device.secret());

		System.out.println("Secret: " + HexHelper.byteArrayToHexString(device.secret()));
		System.out.println("HMAC-SHA1: " + HexHelper.byteArrayToHexString(hmac));

		if (Arrays.equals(hmac, challengeHash)) {
			return true;
		}
		
		return false;
	}


	private QrAction findAction(Byte givenAction)
	{
		for (QrAction action : QrAction.values()) {
			if (action.value() == givenAction) {
				return action;
			}
		}
		
		return null;
	}


    private HttpSession findSessionById(String sessionId)
    {
		ServletContext context = this.getServletContext();
		HttpSession session = (HttpSession) context.getAttribute(sessionId);
		return session;
    }

	
	private void sendError(String message) throws IOException
	{
		this.response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		System.err.println(message);
		this.response.getWriter().println(message);
	}

}
