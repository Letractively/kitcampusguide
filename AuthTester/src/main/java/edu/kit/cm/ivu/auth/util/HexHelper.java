package main.java.edu.kit.cm.ivu.auth.util;

public class HexHelper
{
    public static byte[] hexStringToByteArray(String s)
    {
        int len = s.length();
        byte[] data = new byte[len / 2];
        
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }
        
        return data;
    }


	public static String byteArrayToHexString(byte[] b)
	{
		StringBuffer sb = new StringBuffer(b.length * 2);
		
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		
		return sb.toString();
	  }
}
