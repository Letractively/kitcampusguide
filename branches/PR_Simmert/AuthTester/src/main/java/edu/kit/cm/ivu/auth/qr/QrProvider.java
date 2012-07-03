package main.java.edu.kit.cm.ivu.auth.qr;

public enum QrProvider
{
	KCG ((byte) 0x1);

	private byte value;
	
	
	QrProvider(byte value)
	{
		this.value = value;
	}

	
	public byte value()
	{
		return this.value;
	}
}
