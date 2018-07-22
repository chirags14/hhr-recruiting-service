package com.hhr.group.recruiting.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.hhr.group.recruiting.entity.ApplicationDetail;
import com.hhr.group.recruiting.entity.ApplicationDetail.ApplicationStatus;
import com.hhr.group.recruiting.entity.ApplicationId;
import com.hhr.group.recruiting.entity.OfferCatalog;
import com.hhr.group.recruiting.model.HRRecruitingApplicationDTO;
import com.hhr.group.recruiting.model.HRRecruitingOfferDTO;
import com.hhr.group.recruiting.repository.HRRecruitingApplicationDetailRepository;
import com.hhr.group.recruiting.repository.HRRecruitingOfferCatalogRepository;

/**
 * @author chirag suthar
 * 
 * Service to process all application endpoint , will make use of applicationDetailRepository
 *
 */
@Service
public class HRRecruitingApplicationService {

	/**
	 * applicationDetailRepository
	 */
	@Autowired
	HRRecruitingApplicationDetailRepository applicationDetailRepository;

	/**
	 * offerCatalogRepository
	 */
	@Autowired
	HRRecruitingOfferCatalogRepository offerCatalogRepository;

	/**
	 * @param application
	 * @return
	 * Creates new application
	 */
	public boolean createApplication(HRRecruitingApplicationDTO application) {
		String jobTitle = application.getJobTitle();
		Optional<OfferCatalog> offerCatalogOption = offerCatalogRepository.findById(jobTitle);
		// Check first if candidate applying for offer is exists or not.IF not exists
		// then return false , if exists then add application to offer
		if (offerCatalogOption.isPresent()) {
			ApplicationDetail applicationDetail = new ApplicationDetail();
			ApplicationId applicationId = new ApplicationId();
			applicationId.setJobTitle(jobTitle);
			applicationId.setEmailId(application.getEmailId());
			applicationDetail.setApplicationId(applicationId);
			applicationDetail.setResumeText(application.getResumeText());
			applicationDetail.setApplicationStatus(ApplicationStatus.APPLIED);
			OfferCatalog offerCatalog = offerCatalogOption.get();
			offerCatalog.addApplication(applicationDetail);
			offerCatalogRepository.save(offerCatalog);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param jobTitle
	 * @param emailId
	 * @return
	 * 
	 * Fetch single application based on applicationId(composite key for application thats jobTitle and emailId)
	 */
	public Optional<HRRecruitingApplicationDTO> getApplicationByApplicationId(String jobTitle, String emailId) {
		ApplicationId applicationId = new ApplicationId();
		applicationId.setJobTitle(jobTitle);
		applicationId.setEmailId(emailId);
		Optional<ApplicationDetail> applicationDetailOption = applicationDetailRepository.findById(applicationId);
		HRRecruitingApplicationDTO application = new HRRecruitingApplicationDTO();
		;
		ApplicationDetail applicationDetail = null;
		if (applicationDetailOption.isPresent()) {
			applicationDetail = applicationDetailOption.get();
			application.setJobTitle(jobTitle);
			application.setEmailId(emailId);
			application.setResumeText(applicationDetail.getResumeText());
			application.setApplicationStatus(applicationDetail.getApplicationStatus().toString());
			return Optional.of(application);
		}
		return Optional.ofNullable(application);
	}

	/**
	 * @param jobTitle
	 * @return
	 * 
	 * get all applications by job title
	 */
	public Optional<List<HRRecruitingApplicationDTO>> getAllApplicationsByJobTitle(String jobTitle) {
		List<HRRecruitingApplicationDTO> applications = null;
		HRRecruitingApplicationDTO application = null;
		Optional<List<ApplicationDetail>> applicationDetails = applicationDetailRepository
				.findAllApplicationsByJobTitle(jobTitle);
		// if(applicationDetails.size()>0) {
		if (applicationDetails.isPresent()) {
			applications = new ArrayList<>();
			for (ApplicationDetail applicationDetail : applicationDetails.get()) {
				application = new HRRecruitingApplicationDTO();
				application.setJobTitle(jobTitle);
				application.setEmailId(applicationDetail.getApplicationId().getEmailId());
				application.setResumeText(applicationDetail.getResumeText());
				application.setApplicationStatus(applicationDetail.getApplicationStatus().toString());
				applications.add(application);
			}
			return Optional.of(applications);
		} else {
			return Optional.ofNullable(applications);
		}
	}

	/**
	 * @param application
	 * @return
	 * 
	 * process application by changing application status
	 */
	@Transactional
	@Modifying
	public boolean progressApplicationStatus(HRRecruitingApplicationDTO application) {
		// validate application status which is requested to change against
		// accepted type (APPLIED, INVITED, REJECTED, HIRED)
		boolean isUpdated = false;
		String status = application.getApplicationStatus();
		if (ApplicationStatus.contains(status)) {
			ApplicationId applicationId = new ApplicationId();
			applicationId.setEmailId(application.getEmailId());
			applicationId.setJobTitle(application.getJobTitle());
			Optional<ApplicationDetail> applicationDetailOptional = applicationDetailRepository.findById(applicationId);
			if (applicationDetailOptional.isPresent()) {
				ApplicationDetail applicationDetail = applicationDetailOptional.get();
				applicationDetail.setApplicationStatus(ApplicationStatus.valueOf(status));
				applicationDetailRepository.save(applicationDetail);
				isUpdated = true;
			}
		}
		return isUpdated;
	}

	/**
	 * @param jobTitle
	 * @return
	 * 
	 * get total number of application count per Offer
	 */
	public Optional<HRRecruitingOfferDTO> getApplicationCountPerOffer(String jobTitle) {
		HRRecruitingOfferDTO offer = new HRRecruitingOfferDTO();
		offer.setNoOfApplications(applicationDetailRepository.getApplicationCountPerjobTitle(jobTitle));
		return Optional.of(offer);
	}

}
