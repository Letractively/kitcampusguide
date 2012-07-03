package main.java.edu.kit.cm.ivu.auth.db;

public class Challenge
{
	private String id, sessionId;
	private long created = 0;

	
	public Challenge(String id, String sessionId)
	{
		this.id = id;
		this.sessionId = sessionId;
		this.created = System.currentTimeMillis() / 1000L;
	}
	
	
	public String id()
	{
		return this.id;
	}
	
	
	public String sessionId()
	{
		return this.sessionId;
	}

	
	public long created()
	{
		return this.created;
	}
}
