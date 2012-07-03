package main.java.edu.kit.cm.ivu.auth.db;

import main.java.edu.kit.cm.ivu.auth.User;

public class UserRepository
{
	public static final byte[] DUMMY_ID = new byte[] {0x47, 0x11};


	public static User findById(int id)
	{
		return new User(id, "asimmert", "Simmert", "André");
	}
}
