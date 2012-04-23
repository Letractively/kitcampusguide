package edu.kit.pse.ass.gui.model;

/**
 * The SearchFormModel represents the date entered in the search form
 * 
 * @author Oliver Schneider
 */
public class SearchFormModel extends BookingInfoModel {

	/** The workplace count entered. */
	private int workplaceCount;

	/** The search text entered. */
	private String searchText;

	/**
	 * Instantiates a new search form model with workplaceCount of one and empty searchText.
	 */
	public SearchFormModel() {
		super();
		workplaceCount = 1;
		searchText = "";
	}

	/**
	 * Gets the entered workplace count.
	 * 
	 * @return the workplace count
	 */
	public int getWorkplaceCount() {
		return workplaceCount;
	}

	/**
	 * Sets the entered workplace count.
	 * 
	 * @param workplaceCount
	 *            the new workplace count
	 */
	public void setWorkplaceCount(int workplaceCount) {
		this.workplaceCount = workplaceCount;
	}

	/**
	 * Gets the search text.
	 * 
	 * @return the search text
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * Sets the search text.
	 * 
	 * @param searchText
	 *            the new search text
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
