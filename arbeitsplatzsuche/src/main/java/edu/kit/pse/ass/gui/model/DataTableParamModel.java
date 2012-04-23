package edu.kit.pse.ass.gui.model;

import javax.servlet.http.HttpServletRequest;

/**
 * This class represents the this of the jQuery DataTable.
 */
public class DataTableParamModel {

	/** the request sequence number sent by DataTable. */
	private String sEcho;

	/** the searchText. */
	private String sSearch;

	/** the number of entries shown in the table. */
	private int iDisplayLength;

	/** the first entry to be shown (used for paging). */
	private int iDisplayStart;

	/** the number of columns. */
	private int iColumns;

	/** the number of columns used in sorting. */
	private int iSortingCols;

	/** the index of the column currently sorted. */
	private int iSortColumnIndex;

	/** the sorting direction ("asc" or desc"). */
	private String sSortDirection;

	/** list of column names (separated by a comma). */
	private String sColumns;

	/**
	 * Constructs a new DataTableParamModel setting empty values for Strings and
	 * 0 for integers.
	 */
	public DataTableParamModel() {
		sEcho = "";
		sSearch = "";
		iDisplayLength = 0;
		iDisplayStart = 0;
		iColumns = 0;
		iSortingCols = 0;
		iSortColumnIndex = 0;
		sSortDirection = "";
		sColumns = "";
	}

	/**
	 * Constructs a new DataTableParamModel using the values of the specified
	 * request object
	 * 
	 * @param request
	 *            the request Object with the values for DataTable
	 */
	public DataTableParamModel(HttpServletRequest request) {
		if (request.getParameter("sEcho") != null
				&& request.getParameter("sEcho") != "") {
			// request contains parameters for DataTable
			this.sEcho = request.getParameter("sEcho");
			this.sSearch = request.getParameter("sSearch");
			this.sColumns = request.getParameter("sColumns");
			this.iDisplayStart = Integer.parseInt(request
					.getParameter("iDisplayStart"));
			this.iDisplayLength = Integer.parseInt(request
					.getParameter("iDisplayLength"));
			this.iColumns = Integer.parseInt(request.getParameter("iColumns"));
			this.iSortingCols = Integer.parseInt(request
					.getParameter("iSortingCols"));
			this.iSortColumnIndex = Integer.parseInt(request
					.getParameter("iSortCol_0"));
			this.sSortDirection = request.getParameter("sSortDir_0");
		} else {
			// no parameters in the request - set empty values
			sEcho = "";
			sSearch = "";
			iDisplayLength = 0;
			iDisplayStart = 0;
			iColumns = 0;
			iSortingCols = 0;
			iSortColumnIndex = 0;
			sSortDirection = "";
			sColumns = "";
		}
	}

	/**
	 * returns the request sequence number sent by DataTable
	 * 
	 * @return the request sequence number sent by DataTable
	 */
	public String getsEcho() {
		return sEcho;
	}

	/**
	 * sets the request sequence number sent by DataTable
	 * 
	 * @param sEcho
	 *            the request sequence number sent by DataTable
	 */
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	/**
	 * returns the searchText
	 * 
	 * @return the searchText
	 */
	public String getsSearch() {
		return sSearch;
	}

	/**
	 * sets the searchText
	 * 
	 * @param sSearch
	 *            the searchText
	 */
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	/**
	 * returns the number of entries shown in the table
	 * 
	 * @return the number of entries shown in the table
	 */
	public int getiDisplayLength() {
		return iDisplayLength;
	}

	/**
	 * sets the number of entries shown in the table
	 * 
	 * @param iDisplayLength
	 *            the number of entries shown in the table
	 */
	public void setiDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	/**
	 * returns the first entry to be shown
	 * 
	 * @return the first entry to be shown
	 */
	public int getiDisplayStart() {
		return iDisplayStart;
	}

	/**
	 * sets the first entry to be shown
	 * 
	 * @param iDisplayStart
	 *            the first entry to be shown
	 */
	public void setiDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	/**
	 * returns the number of columns
	 * 
	 * @return the number of columns
	 */
	public int getiColumns() {
		return iColumns;
	}

	/**
	 * sets the number of columns
	 * 
	 * @param iColumns
	 *            the number of columns
	 */
	public void setiColumns(int iColumns) {
		this.iColumns = iColumns;
	}

	/**
	 * returns the number of columns used in sorting
	 * 
	 * @return the number of columns used in sorting
	 */
	public int getiSortingCols() {
		return iSortingCols;
	}

	/**
	 * sets the number of columns used in sorting
	 * 
	 * @param iSortingCols
	 *            the number of columns used in sorting
	 */
	public void setiSortingCols(int iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	/**
	 * returns the index of the column currently sorted
	 * 
	 * @return the index of the column currently sorted
	 */
	public int getiSortColumnIndex() {
		return iSortColumnIndex;
	}

	/**
	 * sets the index of the column currently sorted
	 * 
	 * @param iSortColumnIndex
	 *            the index of the column currently sorted
	 */
	public void setiSortColumnIndex(int iSortColumnIndex) {
		this.iSortColumnIndex = iSortColumnIndex;
	}

	/**
	 * returns the sorting direction
	 * 
	 * @return the sorting direction
	 */
	public String getsSortDirection() {
		return sSortDirection;
	}

	/**
	 * sets the sorting direction
	 * 
	 * @param sSortDirection
	 *            the sorting direction
	 */
	public void setsSortDirection(String sSortDirection) {
		this.sSortDirection = sSortDirection;
	}

	/**
	 * returns list of column names
	 * 
	 * @return list of column names
	 */
	public String getsColumns() {
		return sColumns;
	}

	/**
	 * sets list of column names
	 * 
	 * @param sColumns
	 *            list of column names
	 */
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}

}
