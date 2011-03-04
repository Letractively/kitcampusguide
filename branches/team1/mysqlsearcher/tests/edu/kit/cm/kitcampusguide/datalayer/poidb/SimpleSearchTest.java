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
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SimpleSearchTest {

	private static final String dbURL = "jdbc:mysql://localhost:3306/testdb?user=root&password=pwd";
	private static String _testDir        = "tests/testDB";
    private static String _dbFile         = "dbSimpleSearchTest.xml";
    private static String _driverClass    = "com.mysql.jdbc.Driver";
    private static File file = new File(_testDir, _dbFile);
	
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
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
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
