package main.java.edu.kit.cm.ivu.auth.db;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServlet;


import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;

public class ChallengeRepository
{
	private ObjectContainer db;
	
	
	public ChallengeRepository(HttpServlet servlet)
	{
		this.db = Db4oEmbedded.openFile(servlet.getServletContext().getRealPath("WEB-INF") + File.separator + "db.db4o");
	}
	
	
	public Challenge findById(final String id)
	{
		@SuppressWarnings("serial")
		List <Challenge> challenges = this.db.query(new Predicate<Challenge>() {
			public boolean match(Challenge challenge) {
		        return challenge.id().equals(id);
		    }
		});
		
		if (challenges.size() == 1) {
			return challenges.get(0);
		}
		
		return null;
	}
	
	
	public void add(Challenge challenge)
	{
    	try {
    		this.db.store(challenge);
    	} catch (Exception e) {}
	}
	
	
	public void close()
	{
		this.db.close();
	}

}
