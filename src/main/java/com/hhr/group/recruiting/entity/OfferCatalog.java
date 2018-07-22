package com.hhr.group.recruiting.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author chirag suthar
 * 
 *         Entity class to hold offers and has one to many relationship to
 *         applicationDetails ,so there can be multiple applications for single
 *         offer based on jobTitle
 *
 */
@Entity
@Table(name = "OFFER_CATALOG")
public class OfferCatalog {

	/**
	 * jobTitle
	 */
	@Id
	@Column(name = "JOB_TITLE")
	private String jobTitle;

	/**
	 * startDate
	 */
	@Column(name = "START_DT")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	/**
	 * jobDescription
	 */
	@Column(name = "JOB_DESC")
	private String jobDescription;

	/**
	 * applicationDetails
	 */
	@OneToMany(mappedBy = "applicationId.jobTitle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<ApplicationDetail> applicationDetails = new ArrayList<>();

	/**
	 * @return the jobTitle
	 */
	public final String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @param jobTitle
	 *            the jobTitle to set
	 */
	public final void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * @return the startDate
	 */
	public final Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public final void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the jobDescription
	 */
	public final String getJobDescription() {
		return jobDescription;
	}

	/**
	 * @param jobDescription
	 *            the jobDescription to set
	 */
	public final void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public void addApplication(ApplicationDetail applicationDetail) {
		applicationDetails.add(applicationDetail);
	}

	/**
	 * @return the applicationDetails
	 */
	public final List<ApplicationDetail> getApplicationDetails() {
		return applicationDetails;
	}

	/**
	 * @param applicationDetails
	 *            the applicationDetails to set
	 */
	public final void setApplicationDetails(List<ApplicationDetail> applicationDetails) {
		this.applicationDetails = applicationDetails;
	}

	public OfferCatalog() {

	}

}
