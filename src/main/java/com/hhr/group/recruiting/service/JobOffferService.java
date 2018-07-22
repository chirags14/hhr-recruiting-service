package com.hhr.group.recruiting.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhr.group.recruiting.entity.OfferCatalog;
import com.hhr.group.recruiting.model.Offer;
import com.hhr.group.recruiting.repository.OfferCatalogRepository;

/**
 * @author chirag suthar
 * 
 * Service to process all offers endpoint , will make use of offerCatalogRepository
 *
 */
@Service
public class JobOffferService {

	/**
	 * offerCatalogRepository
	 */
	@Autowired
	OfferCatalogRepository offerCatalogRepository;

	/**
	 * @param offer
	 * create new offer
	 */
	public void createOffer(Offer offer) {
		OfferCatalog offerCatalog = new OfferCatalog();
		offerCatalog.setJobTitle(offer.getJobTitle());
		offerCatalog.setStartDate(offer.getStartDate());
		offerCatalog.setJobDescription(offer.getJobDescription());
		offerCatalogRepository.save(offerCatalog);
	}

	/**
	 * @return
	 * 
	 * get all created offers
	 */
	public Optional<List<Offer>> getAllOffers() {
		List<OfferCatalog> offers = offerCatalogRepository.findAll();
		List<Offer> offferDTOs = new ArrayList<>();
		Offer offer = null;
		for (OfferCatalog offerCatalog : offers) {
			offer = new Offer();
			offer.setJobTitle(offerCatalog.getJobTitle());
			offer.setStartDate(offerCatalog.getStartDate());
			offer.setJobDescription(offerCatalog.getJobDescription());
			offer.setNoOfApplications(offerCatalog.getApplicationDetails().size());
			offferDTOs.add(offer);
		}
		if (offferDTOs.size() > 0) {
			return Optional.of(offferDTOs);
		} else {
			return Optional.ofNullable(offferDTOs);
		}
	}

	/**
	 * @param jobTitle
	 * @return
	 * 
	 * get offer by jobTitle
	 */
	public Optional<Offer> getOfferByJobTitle(String jobTitle) {
		Optional<OfferCatalog> offerOption = offerCatalogRepository.findById(jobTitle);
		Offer offer = null;
		if (offerOption.isPresent()) {
			OfferCatalog offerCatalog = offerOption.get();
			offer = new Offer();
			offer.setJobTitle(offerCatalog.getJobTitle());
			offer.setStartDate(offerCatalog.getStartDate());
			offer.setJobDescription(offerCatalog.getJobDescription());
			offer.setNoOfApplications(offerCatalog.getApplicationDetails().size());
			return Optional.of(offer);
		} else {
			return Optional.ofNullable(offer);
		}
	}

}
