package main.java.edu.kit.cm.ivu.auth.qr;

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
	
	
	public byte[] challenge()
	{
		return this.challenge;
	}
}
