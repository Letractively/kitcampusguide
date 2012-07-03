package main.java.edu.kit.cm.ivu.auth.qr;

public enum QrAction
{
	REGISTER ((byte) 0x1),
	LOGIN    ((byte) 0x2);

	private byte value;
	
	
	QrAction(byte value)
	{
		this.value = value;
	}

	
	public byte value()
	{
		return this.value;
	}
}
