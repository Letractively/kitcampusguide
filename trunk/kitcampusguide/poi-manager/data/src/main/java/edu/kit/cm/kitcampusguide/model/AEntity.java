package edu.kit.cm.kitcampusguide.model;

import java.util.Date;

/**
 * Offers getters and setters for basic persistent fields.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public abstract class AEntity {

	private Integer uid;
	private String createdBy;
	private Date dateCreated;
	private String lastUpdatedBy;
	private Date dateLastUpdated;

	public Integer getUid() {
		return uid;
	}

	public void setUid(final Integer uid) {
		this.uid = uid;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(final Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(final String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}

	public void setDateLastUpdated(final Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

}
