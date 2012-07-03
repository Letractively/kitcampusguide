package main.java.edu.kit.cm.ivu.auth.db;

public class Device
{
	
	private String id, name;
	private byte[] ownerUserId;
	private byte[] secret;
	
	
	public Device(String id)
	{
		this.id = id;
	}
	
	
	public String id() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String name() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public byte[] ownerUserId() {
		return ownerUserId;
	}


	public void setOwnerUserId(byte[] ownerUserId) {
		this.ownerUserId = ownerUserId;
	}


	public byte[] secret() {
		return secret;
	}


	public void setSecret(byte[] secret) {
		this.secret = secret;
	}
}
