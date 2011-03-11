package database;

import java.io.FileInputStream;
import java.sql.Connection;

import junit.framework.TestResult;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import edu.kit.cm.kitcampusguide.data.Config;

	public class DBTest extends DBTestCase
	{
	   public DBTest()
	    {
	   /*
		   System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.postgresql.Driver" );
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:postgresql://localhost:5432/campusguide" );
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "campusguide" );
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "katja" );
		// System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
		 * 
		 */
	    }

	   protected void setUp() throws Exception
	    {
		   System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.postgresql.Driver" );
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:postgresql://localhost:5432/campusguide" );
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "campusguide" );
	        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "katja" );
		
	    }
		public Connection getConn() {
			return Config.getPgSQLJDBCConnection();
		}
		
	    protected IDataSet getDataSet() throws Exception
	    {
	        return new FlatXmlDataSetBuilder().build(new FileInputStream("dataset.xml"));
	    }

	public TestResult run(){
		return null;
		
	}
	}
