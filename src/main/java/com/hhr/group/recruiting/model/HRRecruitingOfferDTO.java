package com.hhr.group.recruiting.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * * @author chirag suthar
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HRRecruitingOfferDTO {
	
	/**
	 * jobTitle
	 */
	@NotNull
	@NotEmpty
	private String jobTitle;
	
	/**
	 * startDate
	 */
	@NotNull
	private Date startDate;
	
	/**
	 * jobDescription
	 */
	@NotNull
	@NotEmpty
	private String jobDescription;
	
	/**
	 * noOfApplications
	 */
	private int noOfApplications;
	
	/**
	 * applications
	 */
	private List<HRRecruitingApplicationDTO> applications ;


	/**
	 * @return the jobTitle
	 */
	public final String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @param jobTitle the jobTitle to set
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
	 * @param startDate the startDate to set
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
	 * @param jobDescription the jobDescription to set
	 */
	public final void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	/**
	 * @return the applications
	 */
	public final List<HRRecruitingApplicationDTO> getApplications() {
		return applications;
	}

	/**
	 * @param applications the applications to set
	 */
	public final void setApplications(List<HRRecruitingApplicationDTO> applications) {
		this.applications = applications;
	}

	/**
	 * @return the noOfApplications
	 */
	public final int getNoOfApplications() {
		return noOfApplications;
	}

	/**
	 * @param noOfApplications the noOfApplications to set
	 */
	public final void setNoOfApplications(int noOfApplications) {
		this.noOfApplications = noOfApplications;
	}

}
