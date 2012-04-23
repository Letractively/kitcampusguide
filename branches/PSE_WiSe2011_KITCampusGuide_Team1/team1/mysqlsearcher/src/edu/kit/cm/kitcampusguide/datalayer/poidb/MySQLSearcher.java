package edu.kit.cm.kitcampusguide.datalayer.poidb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * @author Stefan
 *
 */
public class MySQLSearcher implements POIDBSearcher {

	private final static Logger logger = Logger.getLogger(MySQLSearcher.class);
	
	@Override
	public List<Integer> getIDs(String text, String dbURL) {
		try {
			Connection con = DriverManager.getConnection(dbURL);
			String query = "SELECT id FROM POIDB WHERE name = ?;";
			PreparedStatement statement = con.prepareStatement(query);
			statement.setString(1, text);
			statement.execute();
			ArrayList<Integer> result = new ArrayList<Integer>();
			ResultSet resultSet = statement.getResultSet();
			while (resultSet.next()) {
				result.add(resultSet.getInt(1));
			}
			statement.close();
			if (result.isEmpty()) {
				// No direct matches, search using the MySQL match method
				query = "SELECT id FROM POIDB WHERE MATCH(name) AGAINST (?);";
				statement = con.prepareStatement(query);
				statement.setString(1, text);
				statement.execute();
				resultSet = statement.getResultSet();
				while (resultSet.next()) {
					result.add(resultSet.getInt(1));
				}	
			}
			statement.close();
			con.close();
			return result;
		} catch (SQLException e) {
			logger.error(e);
			return Collections.<Integer> emptyList();
		}
	}
}
