package com.hhr.group.recruiting.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import com.hhr.group.recruiting.listner.HRRecruitingAppStatusEventListner;

/**
 * @author chirag suthar
 * 
 *         Entity class to hold applications , It has composite primary key in
 *         form of ApplicationId (jobTitle , emailId), so that candidate
 *         application can be fetched uniquely. Here ApplicationStatus will be
 *         enum type accepting valid application status codes (APPLIED,
 *         INVITED, REJECTED, HIRED).
 *         Any change in application status will be monitored by ApplicationStatusEventListner
 *
 */
@Entity
@Table(name = "APPLICATION_DETAIL")
@EntityListeners(HRRecruitingAppStatusEventListner.class)
public class ApplicationDetail implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * applicationId
	 */
	@EmbeddedId
	private ApplicationId applicationId;

	/**
	 * resumeText
	 */
	@Column(name = "RESUME_TXT")
	private String resumeText;

	/**
	 * applicationStatus
	 */
	@Column(name = "APPLICATION_STATUS")
	private ApplicationStatus applicationStatus;
	
	
	
	/**
	 *
	 * ApplicationStatus -ENUM
	 */
	public enum ApplicationStatus {
		APPLIED, INVITED, REJECTED, HIRED;
		public static boolean contains(String status) {
			try {
				ApplicationStatus.valueOf(status);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	/**
	 * @return the resumeText
	 */
	public final String getResumeText() {
		return resumeText;
	}

	/**
	 * @param resumeText
	 *            the resumeText to set
	 */
	public final void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}

	/**
	 * @return the applicationStatus
	 */
	public final ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}

	/**
	 * @param applicationStatus
	 *            the applicationStatus to set
	 */
	public final void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	/**
	 * @return the applicationId
	 */
	public final ApplicationId getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId
	 *            the applicationId to set
	 */
	public final void setApplicationId(ApplicationId applicationId) {
		this.applicationId = applicationId;
	}

	public ApplicationDetail() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((applicationId == null) ? 0 : applicationId.hashCode());
		result = prime * result + ((applicationStatus == null) ? 0 : applicationStatus.hashCode());
		result = prime * result + ((resumeText == null) ? 0 : resumeText.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationDetail other = (ApplicationDetail) obj;
		if (applicationId == null) {
			if (other.applicationId != null)
				return false;
		} else if (!applicationId.equals(other.applicationId))
			return false;
		if (applicationStatus != other.applicationStatus)
			return false;
		if (resumeText == null) {
			if (other.resumeText != null)
				return false;
		} else if (!resumeText.equals(other.resumeText))
			return false;
		return true;
	}
}
