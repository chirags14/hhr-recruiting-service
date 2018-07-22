package com.hhr.group.recruiting.listner;

import javax.persistence.PostUpdate;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hhr.group.recruiting.entity.ApplicationDetail;

/**
 * @author chirag suthar
 * 
 *         Entity listner class for ApplicationDetails , So listner will be
 *         handling events in case of there is a change in candidate application
 *         changes such as change in application status from/To (APPLIED,
 *         INVITED, REJECTED, HIRED)
 *
 */
@Component
public class HRRecruitingAppStatusEventListner {

	/**
	 * LOG
	 */
	final static Logger LOG = Logger.getLogger(HRRecruitingAppStatusEventListner.class);

	/**
	 * @param applicationDetail
	 * 
	 *            As requirement  this method will just log (*) in case of any change in
	 *            application status , Later on business logic can be added to
	 *            trigger all together different flow
	 */
	@PostUpdate
	public void postUpdate(ApplicationDetail applicationDetail) {
		LOG.info("(*) Application Status has been changed for application Id "
				+ applicationDetail.getApplicationId().toString());
		LOG.info("(*)");
	}
}
