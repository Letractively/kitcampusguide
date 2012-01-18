package edu.kit.pse.ass.gui.model;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchFormModel.
 * 
 * @author Oliver Schneider
 */
public class SearchFormModel extends BookingInfoModel {

	/** The workplace count. */
	private int workplaceCount;

	/** The search text. */
	private String searchText;

	/**
	 * Instantiates a new search form model.
	 */
	public SearchFormModel() {
		super();
		workplaceCount = 1;
		searchText = "";
	}

	/**
	 * Gets the workplace count.
	 * 
	 * @return the workplace count
	 */
	public int getWorkplaceCount() {
		return workplaceCount;
	}

	/**
	 * Sets the workplace count.
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
