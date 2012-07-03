package edu.kit.cm.ivu.auth.qr;

public class QrLoginMessage extends QrMessage
{
	private byte[] challenge;
	
	
	public QrLoginMessage(QrProvider provider)
	{
		super(provider);
		this.setAction(QrAction.LOGIN);
	}


	protected byte[] body()
	{	
		return this.challenge;
	}
	
	
	public void setChallenge(byte[] challenge)
	{
		this.challenge = challenge;
	}
	
	public byte[] challengeBytes()
	{
		return this.challenge;
	}
	
	public String challenge()
	{
		return byteArrayToHexString(this.challenge);
		
	}
}
