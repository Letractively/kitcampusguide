package main.java.edu.kit.cm.auth.qr;

import java.nio.charset.Charset;

public class QrMessage
{
	public static final byte HEADER_PREFIX = 0x12;
	public static final Charset STRING_CHARSET = Charset.forName("US-ASCII");

	private byte 	   protocol = 0x1;
	private QrProvider provider;
	private QrAction   action;

	
	public QrMessage(QrProvider provider)
	{
		this.provider = provider;
	}
	
	
	public void setProtocol(byte protocol)
	{
		this.protocol = protocol;
	}
	
	
	public void setAction(QrAction action)
	{
		this.action = action;
	}


	public byte[] content()
	{
		byte[] header = this.header(),
			   body   = this.body(),
			   content;
		
		content = new byte[header.length + body.length];

		System.arraycopy(header, 0, content, 0, header.length);
		System.arraycopy(body, 0, content, header.length, body.length);
		
		return content;
	}
	
	
	private byte[] header()
	{
		byte[] header = {
			HEADER_PREFIX,
			this.protocol,
			this.provider.value(),
			this.action.value()
		};
		
		return header;
	}
	
	
	protected byte[] body()
	{
		byte[] content = {};
		
		return content;
	}

	
	public byte protocol()
	{
		return this.protocol;
	}
	
	
	public QrProvider provider()
	{
		return this.provider;
	}
	
	
	public QrAction action()
	{
		return this.action;
	}
	
}
