package main.java.edu.kit.cm.ivu.auth;

public class User
{
	private int id;
	private String username, name, firstname;

	
	public User(int id, String username, String name, String firstname)
	{
		this.id = id;
		this.username = username;
		this.name = name;
		this.firstname = firstname;
	}
	
	
	public int id() {
		return id;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String username() {
		return username;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String name() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String firstname() {
		return firstname;
	}
	
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

}
