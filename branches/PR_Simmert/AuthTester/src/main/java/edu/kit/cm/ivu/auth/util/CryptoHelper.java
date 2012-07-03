package main.java.edu.kit.cm.ivu.auth.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class CryptoHelper
{
	public static byte[] hmacSha1(byte[] value, byte[] key)
	{
		Mac mac;
        SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
        
		try {
			mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
		} catch (Exception e) {
			return null;
		}
        
        return mac.doFinal(value);
    }
}
