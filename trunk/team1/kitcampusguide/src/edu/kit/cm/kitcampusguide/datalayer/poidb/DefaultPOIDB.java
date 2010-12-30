package edu.kit.cm.kitcampusguide.datalayer.poidb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import edu.kit.cm.kitcampusguide.standardtypes.Category;
import edu.kit.cm.kitcampusguide.standardtypes.Map;
import edu.kit.cm.kitcampusguide.standardtypes.MapPosition;
import edu.kit.cm.kitcampusguide.standardtypes.MapSection;
import edu.kit.cm.kitcampusguide.standardtypes.POI;
import edu.kit.cm.kitcampusguide.standardtypes.POIQuery;
import edu.kit.cm.kitcampusguide.standardtypes.WorldPosition;

/**
 * Realizes a {@link POIDB} via a rational database. The database connection is
 * established with JDBC.
 * 
 * @version 1.0
 * @author Stefan
 * 
 */
public class DefaultPOIDB implements POIDB {

	private static POIDB instance;

	/**
	 * Stores the database's URL.
	 */
	private String dbURL;

	/**
	 * Store's the used search algorithm
	 */
	private POIDBSearcher searcher;
	
	/**
	 * Returns the single instance of <code>DefaultPOIDB</code>.
	 * @return a {@link DefaultPOIDB}
	 */
	public static POIDB getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
					"The POIDB must be initialised before.");
		}
		return instance;
	}

	/**
	 * Initializes a <code>DefaultPOIDB</code> working with a given database.
	 * The tables are created if necessary, in this case write access is needed.<br />
	 * An appropriate JDBC driver must be registered. Additionally, the caller
	 * has to specify the used search algorithm.
	 * 
	 * @param dbURL
	 *            the database URL, see {@link DriverManager#getConnection}
	 * @param create
	 *            <code>true</code>, if the method should create all required
	 *            tables. <b>If the tables have been created before they will be
	 *            overriden!</b>
	 * @param searcher
	 *            the search algorithm which is used by this database.
	 * @throws SQLException
	 *             if a database error occurred
	 * @throws NullPointerException
	 *             if <code>dbURL</code> or <code>searcher</code> is
	 *             <code>null</code>
	 * @throws IllegalStateException
	 *             if the POIDB is already initialized
	 */
	public static void init(String dbURL, POIDBSearcher searcher, boolean create) throws SQLException {
		if (instance != null) {
			throw new IllegalStateException("Database already instantiated.");
		}
		Logger logger = Logger.getLogger(DefaultPOIDB.class);
		instance = new DefaultPOIDB(dbURL, searcher, create);
		logger.info("DefaultPOIDB initialized");
	}
	
	/**
	 * Adds a new POI to the database.
	 * 
	 * @param name
	 *            the POI's name.
	 * @param description
	 *            the poi's description.
	 * @param position
	 *            the POI's position on a map.
	 * @param buildingID
	 *            references the appropriate building if the new POI is a
	 *            building POI. Otherwise set this parameter to
	 *            <code>null</code>.
	 * @throws NullPointerException
	 *             <code>name</code>, <code>description</code> or
	 *             <code>position</code> is <code>null</code>
	 * @return <code>true</code>, if the POI was added correctly.
	 *         <code>false</code> otherwise.
	 */
	public boolean addPOI(String name, String description, MapPosition position,
			Integer buildingID, Collection<Category> categories) {
		if (name == null || description == null || position == null) {
			throw new NullPointerException();
		}
		Logger logger = Logger.getLogger(getClass());
		try {
			String query = "INSERT INTO POIDB (name, description, "
					+ "lon, lat, mapid, buildingid) VALUES (?,?,?,?,?,?)";
			Connection connection2 = getConnection();
			PreparedStatement statement = connection2.prepareStatement(query);
			statement.setString(1, name);
			statement.setString(2, description);
			statement.setDouble(3, position.getLongitude());
			statement.setDouble(4, position.getLatitude());
			statement.setInt(5, position.getMap().getID());
			if (buildingID == null) {
				statement.setNull(6, Types.INTEGER);
			}
			else {
				statement.setInt(6, buildingID);
			}
			statement.execute();
			int poiID = statement.getGeneratedKeys().getInt(1);
			statement.close();
			
			query = "INSERT INTO CATEGORY (poiid, categoryid) VALUES (?,?)";
			statement = connection2.prepareStatement(query);
			for (Category c: categories) {
				statement.setInt(1, poiID);
				statement.setInt(2, c.getID());
				statement.addBatch();
			}
			statement.executeBatch();
			statement.close();
			connection2.close();
			logger.debug("POI \"" + name + "\" added (ID:" + poiID + ")");
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public List<POI> getPOIsByQuery(POIQuery query) {
		StringBuilder queryString = new StringBuilder(
				"SELECT id, name, description, lon, lat, mapid, buildingid " +
				"FROM POIDB ");
		boolean needAnd = false;
		// Create map clause if necessary
		if (query.getMaps() != null && !query.getMaps().isEmpty()) {
			queryString.append("WHERE");
			appendMapClause(query.getMaps(), queryString);
			needAnd = true;
		}
		
		// Create category clause if necessary
		if (query.getCategories() != null && !query.getCategories().isEmpty()) {
			queryString.append(needAnd ? "AND" : "WHERE");
			appendCategoryClause(query.getCategories(), queryString);
			needAnd = true;
		}
		
		// Create section clause if necessary 
		if (query.getSection() != null) {
			queryString.append(needAnd ? "AND" : "WHERE");
			appendSectionClause(query, queryString);
		}
		
		List<POI> returnList;
		// Execute the Query
		Logger logger = Logger.getLogger(getClass());
		logger.debug("Generated Query: " + queryString);
		try {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.execute(queryString.toString());
			returnList = new ArrayList<POI>();
			ResultSet result = statement.getResultSet();
			while(result.next()) {
				returnList.add(getPOIByResultSet(result));
			}
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			returnList = Collections.<POI> emptyList();
		}
		return returnList;
	}

	@Override
	public POI getPOIByID(String id) {
		POI result = null;
		try {
			Integer intID = Integer.parseInt(id);
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			String query = "SELECT * FROM POIDB WHERE id = " + intID;
			statement.execute(query);
			result = getPOIByResultSet(statement.getResultSet());
		} catch (SQLException e) {
			Logger logger = Logger.getLogger(getClass());
			logger.error(e.getMessage(), e);
		}
		return result;
	}

	@Override
	public List<POI> getPOIsBySearch(String text) {
		List<Integer> ids = searcher.getIDs(text, dbURL);
		List<POI> result = new ArrayList<POI>(ids.size());
		for (Integer id: ids) {
			// TODO: Could be optimized for only using one query
			result.add(getPOIByID(id.toString()));
		}
		return result;
	}

	/**
	 * See {@link DefaultPOIDB#init(String, POIDBSearcher, boolean)} 
	 */
	private DefaultPOIDB(String dbURL, POIDBSearcher searcher, boolean create)
			throws SQLException {
		if (dbURL == null || searcher == null) {
			throw new NullPointerException();
		}
		this.dbURL = dbURL;
		this.searcher = searcher;
		if (create) {
			removeTables();
			createTables();
		}
	}

	/**
	 * Retrieves the database connection.
	 */
	private Connection getConnection() throws SQLException{
		return DriverManager.getConnection(dbURL);
	}
	
	/**
	 * Removes the <code>POIDB</code> and the <code>CATEGORY</code> table if
	 * they exist.
	 */
	private void removeTables() throws SQLException {
		Logger logger = Logger.getLogger(getClass());
		Statement statement = getConnection().createStatement();
		String query = "DROP TABLE IF EXISTS POIDB";
		statement.execute(query);
		logger.info("POIDB deleted");
		query = "DROP TABLE IF EXISTS CATEGORY";
		statement.execute(query);
		logger.info("CATEGORY deleted");
	}

	/**
	 * Creates the <code>POIDB</code> and the <code>CATEGORY</code> table. The
	 * tables must not exists, otherwise an SQLException is thrown.
	 */
	private void createTables() throws SQLException {

		Logger logger = Logger.getLogger(getClass());
		Statement statement = getConnection().createStatement();
		
		String query = "CREATE TABLE POIDB " 
			+ "(id INTEGER not NULL,"
			+ "name VARCHAR(63) not NULL," 
			+ "description TEXT," 
			+ "lon REAL not NULL,"
			+ "lat REAL not NULL," 
			+ "mapid INTEGER not NULL," 
			+ "buildingid INTEGER,"
			+ "PRIMARY KEY ( id ))";
		statement.execute(query);
		logger.info("POIDB table created");
		
		query = "CREATE TABLE CATEGORY "
			+ "(id INTEGER not NULL,"
			+ "poiid INTEGER not NULL,"
			+ "categoryid INTEGER not NULL," 
			+ "PRIMARY KEY ( id ))";
		statement.execute(query);
		logger.info("CATEGORY table created");
	}

	/**
	 * Creates and appends a clause to a given <code>StringBuilder</code>.
	 * The clause fetch POIs within a given {@link MapSection}.
	 */
	private void appendSectionClause(POIQuery query, StringBuilder whereClause) {
		WorldPosition se = query.getSection().getSouthEast();
		WorldPosition nw = query.getSection().getNorthWest();
		whereClause.append("(lat BETWEEN ");
		whereClause.append(nw.getLatitude());
		whereClause.append(" AND ");
		whereClause.append(se.getLatitude());
		whereClause.append(" AND lon BETWEEN ");
		whereClause.append(se.getLongitude());
		whereClause.append(" AND ");
		whereClause.append(nw.getLongitude());
		whereClause.append(')');
	}

	/**
	 * Creates and appends a clause to a given <code>StringBuilder</code>.
	 * The clause fetches only a given collection of categories.
	 */
	private void appendCategoryClause(Collection<Category> categories,
			StringBuilder whereClause) {
		whereClause
				.append("(id IN (SELECT poiid FROM CATEGORY WHERE categoryid IN (");
		boolean first = true;
		for (Category c : categories) {
			if (!first) {
				whereClause.append(',');
			}
			whereClause.append(c.getID());
			first = false;
		}
		whereClause.append(")))");

	}

	/**
	 * Creates and appends a clause to a given <code>StringBuilder</code>.
	 * The clause fetches only a given collection of {@link Map}s.
	 */
	private void appendMapClause(Collection<Map> maps, StringBuilder whereClause) {
		boolean first = true;
		whereClause.append("(mapid IN (");
		for (Map m : maps) {
			if (!first) {
				whereClause.append(",");
			}
			whereClause.append(m.getID());
			first = false;
		}
		whereClause.append("))");
	}

	/**
	 * Creates a POI from a <code>ResultSet</code> received by the
	 * database.
	 */
	private POI getPOIByResultSet(ResultSet set) throws SQLException {
		// Fetch poi data
		int id = set.getInt(1);
		String name = set.getString(2);
		String description = set.getString(3);
		double lon = set.getDouble(4);
		double lat = set.getDouble(5);
		int mapID = set.getInt(6);
		Integer buildingID = set.getInt(7);
		
		// Fetch the categories
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		String query = "SELECT categoryid FROM CATEGORY WHERE poiid = " + id;
		statement.execute(query);
		ResultSet categories = statement.getResultSet();
		ArrayList<Integer> categoryIDs = new ArrayList<Integer>();
		while (categories.next()) {
			categoryIDs.add(categories.getInt(1));
		}
		
		return new POI(String.valueOf(id), name, description,
				new WorldPosition(lon, lat), Map.getMapByID(mapID), buildingID,
				Category.getCategoriesByIDs(categoryIDs));
	}	
}
