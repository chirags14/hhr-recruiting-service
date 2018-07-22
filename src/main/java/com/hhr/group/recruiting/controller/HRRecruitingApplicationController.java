package com.hhr.group.recruiting.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hhr.group.recruiting.model.HRRecruitingApplicationDTO;
import com.hhr.group.recruiting.service.HRRecruitingApplicationService;

/**
 * @author chirag suthar
 * 
 *         REST Controller class contains endpoints for Application service ,
 *         These endpoints will be responsible for created , fetching and
 *         progressing applications for job offer
 *
 */
@RestController
@ControllerAdvice
public class HRRecruitingApplicationController extends ResponseEntityExceptionHandler {

	/**
	 * LOG
	 */
	final static Logger LOG = Logger.getLogger(HRRecruitingOfferController.class);

	/**
	 * applicationService
	 */
	@Autowired
	private HRRecruitingApplicationService applicationService;

	/**
	 * @param application
	 * @return
	 * 
	 * 		Creates new application for given job title for candidate
	 */
	@RequestMapping(value = "/applications", method = RequestMethod.POST)
	public ResponseEntity<String> createApplication(@Valid @RequestBody HRRecruitingApplicationDTO application) {
		LOG.info("Recevied request to add application");
		if (applicationService.createApplication(application)) {
			LOG.info("Application created successfully.... ");
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} else {
			LOG.info(" Unbale to create application againest provided job offer !! ");
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * @param jobTitle
	 * @return List all applications for given job title
	 */
	@RequestMapping(value = "/applications/{jobTitle}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<HRRecruitingApplicationDTO>> getApplicationsByJobTitle(
			@PathVariable("jobTitle") String jobTitle) {
		LOG.debug("Recevied request to get all applications by jobTitle...");

		Optional<List<HRRecruitingApplicationDTO>> applicationOption = applicationService.getAllApplicationsByJobTitle(jobTitle);

		if (applicationOption.isPresent()) {
			return ResponseEntity.ok(applicationOption.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 */
	/**
	 * @param jobTitle
	 * @param emailId
	 * @return
	 * 
	 * 		Fetch single application based on offer jobTitle and candidate
	 *         emailId
	 */
	@RequestMapping(value = "/applications/{jobTitle}/{emailId}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<HRRecruitingApplicationDTO> getApplicationByApplicationId(
			@PathVariable("jobTitle") String jobTitle, @PathVariable("emailId") String emailId) {
		LOG.debug("Recevied request to application by given jobTitle and emailId...");

		Optional<HRRecruitingApplicationDTO> applicationOption = applicationService.getApplicationByApplicationId(jobTitle, emailId);

		if (applicationOption.isPresent()) {
			return ResponseEntity.ok(applicationOption.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * @param application
	 * @return
	 * 
	 * 		Responsible to progress application for given candidate application ,
	 *         based on application status (APPLIED, INVITED, REJECTED, HIRED)
	 */
	@RequestMapping(value = "/applications/status", method = RequestMethod.POST)
	public ResponseEntity<String> progressApplication(@Valid @RequestBody HRRecruitingApplicationDTO application) {
		LOG.info("Recevied request to update status of a application");
		if (applicationService.progressApplicationStatus(application)) {
			LOG.info("Application status has been updated successfully");
			return new ResponseEntity<String>(HttpStatus.OK);
		} else {
			LOG.info("Application status was not updated !!");
			return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
		}
	}

	/*
	 * Method to handle invalid JSON request and send HTTP 400 code in response.
	 * 
	 * @see org.springframework.web.servlet.mvc.method.annotation.
	 * ResponseEntityExceptionHandler#handleMethodArgumentNotValid(org.
	 * springframework.web.bind.MethodArgumentNotValidException,
	 * org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus,
	 * org.springframework.web.context.request.WebRequest)
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOG.debug("Request got failed in validation");
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
