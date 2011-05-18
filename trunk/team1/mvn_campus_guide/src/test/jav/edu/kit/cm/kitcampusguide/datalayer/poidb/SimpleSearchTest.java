package edu.kit.cm.kitcampusguide.datalayer.poidb;

import org.apache.log4j.BasicConfigurator;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import static org.junit.Assert.*;


import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.test.config.TestConfiguration;

public class SimpleSearchTest {

	private static final String dbURL = "jdbc:sqlite:simplesearchtest.db";
    private static String _driverClass    = "org.sqlite.JDBC";
    private static File file = new File(TestConfiguration.DB_PATH, TestConfiguration.DB_SEARCH_TEST);
	
	/**
	 * @throws ClassNotFoundException
	 * @throws DatabaseUnitException
	 * @throws SQLException
	 * @throws MalformedURLException
	 */
	@BeforeClass
	public static void getDB() {
		BasicConfigurator.configure();
	}
	
	/**
	 * Cleans up the database before each Test.
	 * @throws ClassNotFoundException
	 * @throws DatabaseUnitException
	 * @throws SQLException
	 * @throws MalformedURLException
	 */
	@Before
	public void cleanDB() throws ClassNotFoundException, DatabaseUnitException, SQLException, MalformedURLException {
		IDatabaseConnection connection = getConnection();
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        builder.setDtdMetadata(true);
        IDataSet dataSet = builder.build(file);
        
        Statement statement = DriverManager.getConnection(dbURL).createStatement();
        
        String query = "DROP TABLE IF EXISTS POIDB";
		statement.execute(query);
		query = "DROP TABLE IF EXISTS CATEGORY";
		statement.execute(query);
        
		query = "CREATE TABLE POIDB " 
			+ "(id INTEGER not NULL,"
			+ "name VARCHAR(63) not NULL," 
			+ "description TEXT," 
			+ "lon REAL not NULL,"
			+ "lat REAL not NULL," 
			+ "mapid INTEGER not NULL," 
			+ "buildingid INTEGER,"
			+ "PRIMARY KEY ( id ))";
		statement.execute(query);
		
		
		query = "CREATE TABLE CATEGORY "
			+ "(id INTEGER not NULL,"
			+ "poiid INTEGER not NULL,"
			+ "categoryid INTEGER not NULL," 
			+ "PRIMARY KEY ( id ))";
		statement.execute(query);
        
        DatabaseOperation.REFRESH.execute(connection, dataSet);
	}
	
	
	/**
	 * 
	 */
	@Test
	public void testGetIDs() {
		SimpleSearch searcher = new SimpleSearch();
		
		List<Integer> result = searcher.getIDs("hello1", dbURL);
		assertEquals(2, result.size());
		assertTrue(result.contains(1));
		assertTrue(result.contains(5));
	}
	
	/**
     * @return IDatabaseConnection
     * @throws ClassNotFoundException
     * @throws DatabaseUnitException
     * @throws SQLException
     */
    public static IDatabaseConnection getConnection() throws  ClassNotFoundException, 
                                                                DatabaseUnitException, 
                                                                SQLException {
        // database connection
        Class driverClass = Class.forName(_driverClass);
        Connection jdbcConnection = DriverManager.getConnection(dbURL);
        return new DatabaseConnection(jdbcConnection);
    }
}
