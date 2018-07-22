/**
 * 
 */
package com.hhr.group.recruiting.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.hhr.group.recruiting.model.Application;
import com.hhr.group.recruiting.model.Offer;

/**
 * @author chira
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferControllerTest {

	private static final String OFFERS_ENDPOINT = "/offers";

	private static final String OFFER_BY_JOB_TITLE_ENDPOINT = "/offers/{jobTitle}";

	private static final String OFFER_APPLICATIONS_COUNT_ENDPOINT = "/offers/{jobTitle}/applications/count";
	
	private static final String APPLICATIONS_ENDPOINT = "/applications";

	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		postOffers();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	private void postOffers() {
		Offer offer = new Offer();
		offer.setJobTitle("Java Developer");
		offer.setStartDate(new Date());
		offer.setJobDescription("Java developer with hands on Spring ,Spring Integration and Hibernate");
		ResponseEntity<?> response = this.restTemplate.postForEntity(OFFERS_ENDPOINT, offer, String.class);
		assertThat("Status unexpected", response.getStatusCode(), is(HttpStatus.CREATED));

		Offer offer2 = new Offer();
		offer2.setJobTitle("Senior Java Developer");
		offer2.setStartDate(new Date());
		offer2.setJobDescription(
				"Java developer with hands on Spring ,Spring Integration and Hibernate with 10+ years of exp");
		ResponseEntity<?> response2 = this.restTemplate.postForEntity(OFFERS_ENDPOINT, offer, String.class);
		assertThat("Status unexpected", response2.getStatusCode(), is(HttpStatus.CREATED));
		
		Offer offer3 = new Offer();
		offer3.setJobTitle("DevOps Engineer");
		offer3.setStartDate(new Date());
		offer3.setJobDescription(
				"DevOps engineer with hands on Jenkins,Ansible ,AppD ,Splunk");
		ResponseEntity<?> response3 = this.restTemplate.postForEntity(OFFERS_ENDPOINT, offer, String.class);
		assertThat("Status unexpected", response3.getStatusCode(), is(HttpStatus.CREATED));
	}

	/**
	 * Test method for
	 * {@link com.hhr.group.recruiting.controller.OfferController#createOffer(com.hhr.group.recruiting.model.Offer)}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testCreateOffer() throws ParseException {
		postOffers();
	}

	/**
	 * Test method for
	 * {@link com.hhr.group.recruiting.controller.OfferController#getAllOffers()}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testGetAllOffers() throws ParseException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(headers);
		ResponseEntity<Object> response = restTemplate.exchange(OFFERS_ENDPOINT, HttpMethod.GET, entity, Object.class);
		assertThat("Status unexpected", response.getStatusCode(), is(HttpStatus.OK));
	}

	/**
	 * Test method for
	 * {@link com.hhr.group.recruiting.controller.OfferController#getOfferByJobTitle(java.lang.String)}.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testGetOfferByJobTitle() throws ParseException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("jobTitle", "Java Developer");
		URI uri = UriComponentsBuilder.fromUriString(OFFER_BY_JOB_TITLE_ENDPOINT).buildAndExpand(params).toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(headers);
		ResponseEntity<Offer> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Offer.class);
		Offer offer = response.getBody();
		assertNotNull(offer);
		assertThat("Offer not valid unexpected", offer.getJobTitle(), is("Java Developer"));
		assertThat("Status unexpected", response.getStatusCode(), is(HttpStatus.OK));

	}

	/**
	 * Test method for
	 * {@link com.hhr.group.recruiting.controller.OfferController#getApplicationsCount(java.lang.String)}.
	 * @throws ParseException 
	 */
	/*@Test
	public void testGetApplicationsCount() throws ParseException {
		//create application on job offer
		Application application1 = new Application();
		application1.setJobTitle("DevOps Engineer");
		application1.setEmailId("candidate@gmail.com");
		application1.setResumeText("Java developer with 6+ years exp in Spring ,Hinernate ,JPA");
		ResponseEntity<?> response1 = this.restTemplate.postForEntity(APPLICATIONS_ENDPOINT, application1,
				String.class);
		assertThat("Status unexpected", response1.getStatusCode(), is(HttpStatus.CREATED));
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("jobTitle", "DevOps Engineer");
		URI uri = UriComponentsBuilder.fromUriString(OFFER_APPLICATIONS_COUNT_ENDPOINT).buildAndExpand(params).toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(headers);
		ResponseEntity<Offer> responseCount = restTemplate.exchange(uri, HttpMethod.GET, entity, Offer.class);
		Offer offerCount = responseCount.getBody();
		assertNotNull(offerCount);
		assertThat("no of application count not mathced", offerCount.getNoOfApplications(), is(1));
		assertThat("Status unexpected", responseCount.getStatusCode(), is(HttpStatus.OK));
	}*/

}
