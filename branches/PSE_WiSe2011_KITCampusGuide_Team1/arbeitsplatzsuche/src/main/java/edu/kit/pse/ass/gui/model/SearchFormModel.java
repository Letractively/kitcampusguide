package edu.kit.pse.ass.gui.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author Oliver Schneider
 * 
 */
public class SearchFormModel {

	private int workplaceCount;
	@DateTimeFormat(pattern = "d-M-y HH:mm")
	private Date start;
	private String duration;
	private boolean wholeRoom;
	private String searchText;

	public SearchFormModel() {
		workplaceCount = 1;
		start = Calendar.getInstance().getTime();
		duration = "1:00";
		wholeRoom = false;
		searchText = "";
	}

	public int getWorkplaceCount() {
		return workplaceCount;
	}

	public void setWorkplaceCount(int workplaceCount) {
		this.workplaceCount = workplaceCount;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public boolean isDurationValid() {
		return duration != null && duration.matches("[0-9]*:?[0-9]*");
	}

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

	public boolean isWholeRoom() {
		return wholeRoom;
	}

	public void setWholeRoom(boolean wholeRoom) {
		this.wholeRoom = wholeRoom;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
}
