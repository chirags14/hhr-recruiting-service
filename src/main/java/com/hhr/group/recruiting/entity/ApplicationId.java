package com.hhr.group.recruiting.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author chirag suthar
 *
 *Composite primary key for ApplicationDetail
 */
@Embeddable
public class ApplicationId implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * jobTitle
	 */
	@Column(name="JOB_TITLE")
	private String jobTitle;
	
	/**
	 * emailId
	 */
	@Column(name="EMAIL_ID")
	private String emailId;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ApplicationId [jobTitle=" + jobTitle + ", emailId=" + emailId + "]";
	}

}
