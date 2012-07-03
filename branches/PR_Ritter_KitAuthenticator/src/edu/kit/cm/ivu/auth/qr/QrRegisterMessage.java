package edu.kit.cm.ivu.auth.qr;

import java.math.BigInteger;

public class QrRegisterMessage extends QrMessage
{
	public static final char USERID_LENGTH = 2,
							 SECRET_LENGTH = 16;

	private String responseUrl;
	private byte[] userId,
				   secret;

	
	public QrRegisterMessage(QrProvider provider)
	{
		super(provider);
		this.setAction(QrAction.REGISTER);
	}


	protected byte[] body()
	{
		byte[] responseUrl = this.responseUrl.getBytes(QrMessage.STRING_CHARSET);
		byte[] body = new byte[responseUrl.length + this.secret.length + this.userId.length];

		System.arraycopy(this.userId, 0, body, 0, this.userId.length);
		System.arraycopy(this.secret, 0, body, this.userId.length, this.secret.length);
		System.arraycopy(responseUrl, 0, body, this.userId.length + this.secret.length, responseUrl.length);

		return body;
	}


	public void setResponseUrl(String responseUrl)
	{
		this.responseUrl = responseUrl;
	}
	
	
	public String responseUrl()
	{
		return this.responseUrl;
	}


	public void setUserId(byte[] userId)
	{
		this.userId = userId;
	}
	
	
	public String userId()
	{
		return String.valueOf(new BigInteger(userId).intValue());
	}


	public void setSecret(byte[] secret)
	{
		this.secret = secret;
	}
	
	
	public String secret()
	{
		return byteArrayToHexString(this.secret);
	}
}
