package edu.kit.cm.kitcampusguide.datalayer.poidb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements a simple search algorithm which will only find POIs whose names
 * match exactly the search term.
 * 
 * @author Stefan
 * @version 1.0
 */
public class SimpleSearch implements POIDBSearcher {

	@Override
	public List<Integer> getIDs(String text, String dbURL) {
		Connection connection;
		try {
			connection = DriverManager.getConnection(dbURL);
			String query = "SELECT id FROM POIDB WHERE name = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, text);
			statement.execute();
			ArrayList<Integer> result = new ArrayList<Integer>();
			ResultSet resultSet = statement.getResultSet();
			while (resultSet.next()) {
				result.add(resultSet.getInt(1));
			}
			return result;
		} catch (SQLException e) {
			// TODO: Log this correctly
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

}
