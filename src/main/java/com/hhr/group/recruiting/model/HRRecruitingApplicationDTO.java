package com.hhr.group.recruiting.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author chirag suthar
 * 
 * DTO class for Application procesing 
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HRRecruitingApplicationDTO {
	
	/**
	 * jobTitle
	 */
	@NotNull
	@NotEmpty
	private String jobTitle;
	
	/**
	 * emailId
	 */
	@NotNull
	@NotEmpty
	private String emailId;
	
	/**
	 * resumeText
	 */
	private String resumeText;
	
	/**
	 * applicationStatus
	 */
	private String applicationStatus;

	/**
	 * @return the jobTitle
	 */
	public final String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @return the emailId
	 */
	public final String getEmailId() {
		return emailId;
	}

	/**
	 * @return the resumeText
	 */
	public final String getResumeText() {
		return resumeText;
	}

	/**
	 * @return the applicationStatus
	 */
	public final String getApplicationStatus() {
		return applicationStatus;
	}

	/**
	 * @param jobTitle the jobTitle to set
	 */
	public final void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public final void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @param resumeText the resumeText to set
	 */
	public final void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}

	/**
	 * @param applicationStatus the applicationStatus to set
	 */
	public final void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}


}
