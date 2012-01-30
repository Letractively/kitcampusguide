package edu.kit.pse.ass.gui.model;

import java.util.Collection;

/**
 * represents the JSON response containing the search results for DataTables
 * 
 * @author Jannis Koch
 * 
 */
public class SearchResultsModel {

	/** the request sequence number sent by DataTable. */
	private String sEcho;

	/** the number of total records */
	private int iTotalRecords;

	/** the number of the records displayed (might be less than total records due to filtering) */
	private int iTotalDisplayRecords;

	/** error messages */
	private Collection<String> asErrors;

	/**
	 * the search results as collection which contains another collection with the actual data for each row in the table
	 */
	private Collection<Collection<String>> aaData;

	/**
	 * constructs a new SearchResultsModel using the given values
	 * 
	 * @param sEcho
	 *            the request sequence number
	 * @param iTotalRecords
	 *            the number of total records
	 * @param iTotalDisplayRecords
	 *            the number of the records displayed
	 * @param aaData
	 *            the search results
	 */
	public SearchResultsModel(String sEcho, int iTotalRecords, int iTotalDisplayRecords,
			Collection<Collection<String>> aaData) {
		setsEcho(sEcho);
		setiTotalRecords(iTotalRecords);
		setiTotalDisplayRecords(iTotalDisplayRecords);
		setAaData(aaData);
	}

	/**
	 * returns the request sequence number
	 * 
	 * @return the request sequence number
	 */
	public String getsEcho() {
		return sEcho;
	}

	/**
	 * sets the request sequence number
	 * 
	 * @param sEcho
	 *            the request sequence number
	 */
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	/**
	 * returns the number of total records
	 * 
	 * @return the number of total records
	 */
	public int getiTotalRecords() {
		return iTotalRecords;
	}

	/**
	 * sets the number of total records
	 * 
	 * @param iTotalRecords
	 *            the number of total records
	 */
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	/**
	 * returns the number of the records displayed
	 * 
	 * @return the number of the records displayed
	 */
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	/**
	 * sets the number of the records displayed
	 * 
	 * @param iTotalDisplayRecords
	 *            the number of the records displayed
	 */
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	/**
	 * returns the search results
	 * 
	 * @return the search results
	 */
	public Collection<Collection<String>> getAaData() {
		return aaData;
	}

	/**
	 * sets the search results
	 * 
	 * @param aaData
	 *            the search results
	 */
	public void setAaData(Collection<Collection<String>> aaData) {
		this.aaData = aaData;
	}

	/**
	 * returns the error messages
	 * 
	 * @return the error messages
	 */
	public Collection<String> getAsErrors() {
		return asErrors;
	}

	/**
	 * sets the error messages
	 * 
	 * @param asErrors
	 *            the error messages
	 */
	public void setAsErrors(Collection<String> asErrors) {
		this.asErrors = asErrors;
	}
}
