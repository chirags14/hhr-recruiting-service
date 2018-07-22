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

import com.hhr.group.recruiting.model.Offer;
import com.hhr.group.recruiting.service.ApplicationService;
import com.hhr.group.recruiting.service.JobOffferService;

/**
 * @author chirag suthar
 * 
 *         REST Controller to handle Job offers such as creataing new job offers
 *         , fetch all and single job offer and also list out number of job
 *         applicants for given jobTitle
 *
 */
@RestController
@ControllerAdvice
public class OfferController extends ResponseEntityExceptionHandler {

	/**
	 * LOG
	 */
	final static Logger LOG = Logger.getLogger(OfferController.class);

	/**
	 * jobOffferService
	 */
	@Autowired
	private JobOffferService jobOffferService;

	/**
	 * applicationService
	 */
	@Autowired
	private ApplicationService applicationService;

	/**
	 * @param offer
	 * @return endpoint to create new offer
	 */
	@RequestMapping(value = "/offers", method = RequestMethod.POST)
	public ResponseEntity<String> createOffer(@Valid @RequestBody Offer offer) {
		LOG.debug("Recevied request to add offer...");
		jobOffferService.createOffer(offer);
		LOG.debug("Offer created successfully...");
		// if offer is saved successfully based on constraints then return 201
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	/**
	 * @return Endpoint to list out all created offers
	 */
	@RequestMapping(value = "/offers", method = RequestMethod.GET)
	public ResponseEntity<List<Offer>> getAllOffers() {
		LOG.debug("Recevied request to get all offers...");
		Optional<List<Offer>> offers = jobOffferService.getAllOffers();
		if (offers.isPresent()) {
			return ResponseEntity.ok(offers.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * @param jobTitle
	 * @return
	 * 
	 * 		Endpoint to fetch single offer based on jobTitle
	 */
	@RequestMapping(value = "/offers/{jobTitle}", method = RequestMethod.GET)
	public ResponseEntity<Offer> getOfferByJobTitle(@PathVariable("jobTitle") String jobTitle) {
		LOG.debug("Recevied request to get offer by JobTile...");
		Optional<Offer> offer = jobOffferService.getOfferByJobTitle(jobTitle);
		if (offer.isPresent()) {
			return ResponseEntity.ok(offer.get());
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * @param jobTitle
	 * @return
	 * 
	 * 		endpoint to count number of applications based on given jobTitle
	 */
	@RequestMapping(value = "/offers/{jobTitle}/applications/count", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Offer> getApplicationsCount(@PathVariable("jobTitle") String jobTitle) {
		LOG.debug("Recevied request to get applications count for offer...");
		Optional<Offer> offer = applicationService.getApplicationCountPerOffer(jobTitle);
		if (offer.isPresent()) {
			return ResponseEntity.ok(offer.get());
		} else {
			return ResponseEntity.noContent().build();
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
