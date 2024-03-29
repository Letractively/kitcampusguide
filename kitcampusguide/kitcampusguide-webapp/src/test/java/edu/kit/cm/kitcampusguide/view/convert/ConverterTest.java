package edu.kit.cm.kitcampusguide.view.convert;

import javax.faces.convert.ConverterException;

import org.junit.Assert;
import org.junit.Test;

import edu.kit.cm.kitcampusguide.view.converter.POICategoryConverter;
import edu.kit.cm.kitcampusguide.view.converter.POICategoryListConverter;
import edu.kit.cm.kitcampusguide.view.converter.POIConverter;
import edu.kit.cm.kitcampusguide.view.converter.POIListConverter;
import edu.kit.cm.kitcampusguide.view.converter.POINameConverter;
import edu.kit.cm.kitcampusguide.view.converter.RouteConverter;

/**
 * This class tests the POICategoryConverter, POICategoryListConverter,
 * POIConverter, POIListConverter, POINameConverter and RouteConverter classes.
 * 
 * @author Kateryna Yurchenko
 */
public class ConverterTest {

	/**
	 * Test of the getAsObject() method in POICategoryConverter.
	 */
	@Test
	public void testPOICategoryConverterGetAsObject() {
		POICategoryConverter pc = new POICategoryConverter();
		try {
			pc.getAsObject(null, null, "test");
			Assert.fail("ConverterException expected");
		} catch (ConverterException ce) {
			Assert.assertTrue(ce.getCause() instanceof UnsupportedOperationException);
		}
	}
	
	/**
	 * Test of the getAsString() method in POICategoryConverter.
	 */
	@Test
	public void testPOICategoryConverterGetAsString() {
		POICategoryConverter pc = new POICategoryConverter();
		try {
			pc.getAsString(null, null, null);
			Assert.fail("NullPointerException expected");
		} catch (NullPointerException npe) {
		}
	}
	
	/**
	 * Test of the getAsObject() method in POICategoryListConverter.
	 */
	@Test
	public void testPOICategoryListConverterGetAsObject() {
		POICategoryListConverter pcl = new POICategoryListConverter();
		try {
			pcl.getAsObject(null, null, "test");
			Assert.fail("ConverterException expected");
		} catch (ConverterException ce) {
			Assert.assertTrue(ce.getCause() instanceof UnsupportedOperationException);
		}
	}
	
	/**
	 * Test of the getAsString() method in POICategoryListConverter.
	 */
	@Test
	public void testPOICategoryListConverterGetAsString() {
		POICategoryListConverter pcl = new POICategoryListConverter();
		try {
			pcl.getAsString(null, null, null);
			Assert.fail("NullPointerException expected");
		} catch (NullPointerException npe) {
		}
	}
	
	/**
	 * Test of the getAsObject() method in POIConverter.
	 */
	@Test
	public void testPOIConverterGetAsObject() {
		POIConverter pc = new POIConverter();
		try {
			pc.getAsObject(null, null, "test");
			Assert.fail("ConverterException expected");
		} catch (ConverterException ce) {
			Assert.assertTrue(ce.getCause() instanceof UnsupportedOperationException);
		}
	}
	
	/**
	 * Test of the getAsString() method in POIConverter.
	 */
	@Test
	public void testPOIConverterGetAsString() {
		POIConverter pc = new POIConverter();
		try {
			pc.getAsString(null, null, null);
			Assert.fail("NullPointerException expected");
		} catch (NullPointerException npe) {
		}
	}
	
	/**
	 * Test of the getAsObject() method in POIListConverter.
	 */
	@Test
	public void testPOIListConverterGetAsObject() {
		POIListConverter plc = new POIListConverter();
		try {
			plc.getAsObject(null, null, "test");
			Assert.fail("ConverterException expected");
		} catch (ConverterException ce) {
			Assert.assertTrue(ce.getCause() instanceof UnsupportedOperationException);
		}
	}
	
	/**
	 * Test of the getAsString() method in POIListConverter.
	 */
	@Test
	public void testPOIListConverterGetAsString() {
		POIListConverter plc = new POIListConverter();
		try {
			plc.getAsString(null, null, null);
			Assert.fail("NullPointerException expected");
		} catch (NullPointerException npe) {
		}
	}
	
	/**
	 * Test of the getAsObject() method in POINameConverter.
	 */
	@Test
	public void testPOINameConverterGetAsObject() {
		POINameConverter pnc = new POINameConverter();
		try {
			pnc.getAsObject(null, null, "test");
			Assert.fail("NullPointerException expected");
		} catch (NullPointerException npe) {
		}
	}
	
	/**
	 * Test of the getAsString() method in POINameConverter.
	 */
	@Test
	public void testPOINameConverterGetAsString() {
		POINameConverter pnc = new POINameConverter();
		try {
			pnc.getAsString(null, null, null);
			Assert.fail("NullPointerException expected");
		} catch (NullPointerException npe) {
		}
	}
	
	/**
	 * Test of the getAsObject() method in RouteConverter.
	 */
	@Test
	public void testRouteConverterGetAsObject() {
		RouteConverter rc = new RouteConverter();
		try {
			rc.getAsObject(null, null, "test");
			Assert.fail("ConverterException expected");
		} catch (ConverterException ce) {
			Assert.assertTrue(ce.getCause() instanceof UnsupportedOperationException);
		}
	}
	
	/**
	 * Test of the getAsString() method in RouteConverter.
	 */
	@Test
	public void testRouteConverterGetAsString() {
		RouteConverter rc = new RouteConverter();
		try {
			rc.getAsString(null, null, null);
			Assert.fail("NullPointerException expected");
		} catch (NullPointerException npe) {
		}
	}
}
