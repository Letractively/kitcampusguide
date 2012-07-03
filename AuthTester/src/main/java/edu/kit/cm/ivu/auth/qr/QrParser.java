package main.java.edu.kit.cm.ivu.auth.qr;

import java.io.IOException;
import java.util.Arrays;

import sun.misc.BASE64Decoder;

public class QrParser
{
	
	public static QrMessage parse(byte[] base64Code) throws QrException, IOException
	{
		QrMessage message;
		
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] code = decoder.decodeBuffer(base64Code.toString());

		if (!hasValidPrefix(code)) {
			throw new QrException("Invalid prefix.");
		}

		if (!usesSupportedProtocol(code)) {
			throw new QrException("Protocol version not supported.");
		}
		
		message = createMessage(code);
		
		return message;
	}
	
	
	private static QrMessage createMessage(byte[] code) throws QrException
	{
		switch (findAction(code)) {
		case REGISTER:
			return createRegisterMessage(code);
		case LOGIN:
			return createLoginMessage(code);
		}
		
		throw new QrException("Unsupported action.");
	}
	
	
	private static QrRegisterMessage createRegisterMessage(byte[] code)
	{
		byte[] userId = Arrays.copyOfRange(code, 4, 4 + QrRegisterMessage.USERID_LENGTH),
			   secret = Arrays.copyOfRange(code, 4 + QrRegisterMessage.USERID_LENGTH, 4 + QrRegisterMessage.USERID_LENGTH + QrRegisterMessage.SECRET_LENGTH),
			   responseUrl = Arrays.copyOfRange(code, 4 + QrRegisterMessage.USERID_LENGTH + QrRegisterMessage.SECRET_LENGTH, code.length);

		QrRegisterMessage message = new QrRegisterMessage(findProvider(code));
		message.setUserId(userId);
		message.setSecret(secret);
		message.setResponseUrl(new String(responseUrl, QrMessage.STRING_CHARSET));

		return message;
	}


	private static QrLoginMessage createLoginMessage(byte[] code)
	{
		byte[] challenge = Arrays.copyOfRange(code, 4, code.length);

		QrLoginMessage message = new QrLoginMessage(findProvider(code));
		message.setChallenge(challenge);
		
		return message;
	}
	
	
	private static boolean hasValidPrefix(byte[] code)
	{
		if (code[0] == QrMessage.HEADER_PREFIX) {
			return true;
		}

		return false;
	}
	
	
	private static boolean usesSupportedProtocol(byte[] code)
	{
		if (code[1] == 0x1) {
			return true;
		}

		return false;
	}

	
	private static QrProvider findProvider(byte[] code)
	{
		for (QrProvider provider : QrProvider.values()) {
			if (provider.value() == code[2]) {
				return provider;
			}
		}
		
		return null;
	}


	private static QrAction findAction(byte[] code) throws QrException
	{
		for (QrAction action : QrAction.values()) {
			if (action.value() == code[3]) {
				return action;
			}
		}
		
		return null;
	}
	
}
