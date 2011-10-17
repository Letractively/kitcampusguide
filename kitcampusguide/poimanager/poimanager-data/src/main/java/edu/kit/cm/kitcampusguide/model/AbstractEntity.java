package edu.kit.cm.kitcampusguide.model;

import java.util.Date;

/**
 * Offers getters and setters for basic persistent fields.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public abstract class AbstractEntity implements Entity {

	private Integer id;
	private String createdBy;
	private Date dateCreated;
	private String lastUpdatedBy;
	private Date dateLastUpdated;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(final Integer id) {
		this.id = id;
	}

	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public Date getDateCreated() {
		return dateCreated;
	}

	@Override
	public void setDateCreated(final Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@Override
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	@Override
	public void setLastUpdatedBy(final String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Override
	public Date getDateLastUpdated() {
		return dateLastUpdated;
	}

	@Override
	public void setDateLastUpdated(final Date dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

}
