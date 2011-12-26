package edu.kit.pse.ass.gui.model;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Oliver Schneider
 * 
 */
public class SearchFormModel {
	private int workplaceCount;
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
