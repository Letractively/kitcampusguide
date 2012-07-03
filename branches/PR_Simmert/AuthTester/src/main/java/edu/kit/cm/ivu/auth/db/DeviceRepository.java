package main.java.edu.kit.cm.ivu.auth.db;

public class DeviceRepository
{
	public static final byte[] DUMMY_SECRET = new byte[] { 0x50, (byte) 0xa2, (byte) 0xa1, 0x61, 0x03, (byte) 0xea, 0x4d, 0x32, (byte) 0x8f, (byte) 0xf0, (byte) 0xa6, 0x1f, (byte) 0x88, (byte) 0xee, (byte) 0xf3, 0x51 };


	public static Device findByIdAndOwner(String id, int ownerUserId)
	{
		Device device = new Device(id);
		device.setOwnerUserId(UserRepository.DUMMY_ID);
		device.setSecret(DUMMY_SECRET);	
		return device;
	}

}
