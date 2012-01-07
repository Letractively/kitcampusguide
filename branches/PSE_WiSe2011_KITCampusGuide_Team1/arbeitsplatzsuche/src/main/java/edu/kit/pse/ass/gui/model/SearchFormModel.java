package edu.kit.pse.ass.gui.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchFormModel.
 *
 * @author Oliver Schneider
 */
public class SearchFormModel {

	/** The workplace count. */
	private int workplaceCount;
	
	/** The start. */
	@DateTimeFormat(pattern = "d-M-y HH:mm")
	private Date start;
	
	/** The duration. */
	private String duration;
	
	/** The whole room. */
	private boolean wholeRoom;
	
	/** The search text. */
	private String searchText;

	/**
	 * Instantiates a new search form model.
	 */
	public SearchFormModel() {
		workplaceCount = 1;
		start = Calendar.getInstance().getTime();
		duration = "1:00";
		wholeRoom = false;
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
	 * @param workplaceCount the new workplace count
	 */
	public void setWorkplaceCount(int workplaceCount) {
		this.workplaceCount = workplaceCount;
	}

	/**
	 * Gets the start.
	 *
	 * @return the start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * Sets the start.
	 *
	 * @param start the new start
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets the duration.
	 *
	 * @param duration the new duration
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Checks if is duration valid.
	 *
	 * @return true, if is duration valid
	 */
	public boolean isDurationValid() {
		return duration != null && duration.matches("[0-9]*:?[0-9]*");
	}

	/**
	 * Gets the end.
	 *
	 * @return the end
	 * @throws IllegalStateException the illegal state exception
	 */
	public Date getEnd() throws IllegalStateException {
		Calendar result = Calendar.getInstance();

		if (duration.indexOf(':') > 0) {
			String[] tmp = duration.split(":");
			try {
				int hour = Integer.parseInt(tmp[0]);
				int minutes = Integer.parseInt(tmp[1]);
				result.add(Calendar.HOUR, hour);
				result.add(Calendar.MINUTE, minutes);

			} catch (NumberFormatException ex) {
				throw new IllegalStateException("Illegal Duration", ex);
			}
		}

		return result.getTime();

	}

	/**
	 * Checks if is whole room.
	 *
	 * @return true, if is whole room
	 */
	public boolean isWholeRoom() {
		return wholeRoom;
	}

	/**
	 * Sets the whole room.
	 *
	 * @param wholeRoom the new whole room
	 */
	public void setWholeRoom(boolean wholeRoom) {
		this.wholeRoom = wholeRoom;
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
	 * @param searchText the new search text
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
