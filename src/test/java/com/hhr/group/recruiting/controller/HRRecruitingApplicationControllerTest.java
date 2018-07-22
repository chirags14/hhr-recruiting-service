package com.hhr.group.recruiting.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.hhr.group.recruiting.model.HRRecruitingApplicationDTO;
import com.hhr.group.recruiting.model.HRRecruitingOfferDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HRRecruitingApplicationControllerTest {

	private static final String OFFERS_ENDPOINT = "/offers";

	private static final String APPLICATIONS_ENDPOINT = "/applications";

	private static final String APPLICATIONS_BY_JOB_TITLE = "/applications/{jobTitle}";

	private static final String APPLICATION_BY_APPLICATION_ID_ENDPOINT = "/applications/{jobTitle}/{emailId}";
	
	private static final String APPLICATION_PROGRESS_ENDPOINT = "/applications/status";

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		postOffers();
		postApplications();
	}

	@After
	public void tearDown() throws Exception {
	}

	private void postOffers() {
		HRRecruitingOfferDTO offer = new HRRecruitingOfferDTO();
		offer.setJobTitle("Java Developer");
		offer.setStartDate(new Date());
		offer.setJobDescription("Java developer with hands on Spring ,Spring Integration and Hibernate");
		ResponseEntity<?> response = this.restTemplate.postForEntity(OFFERS_ENDPOINT, offer, String.class);
		assertThat("Status unexpected", response.getStatusCode(), is(HttpStatus.CREATED));

		HRRecruitingOfferDTO offer2 = new HRRecruitingOfferDTO();
		offer2.setJobTitle("Senior Java Developer");
		offer2.setStartDate(new Date());
		offer2.setJobDescription(
				"Java developer with hands on Spring ,Spring Integration and Hibernate with 10+ years of exp");
		ResponseEntity<?> response2 = this.restTemplate.postForEntity(OFFERS_ENDPOINT, offer, String.class);
		assertThat("Status unexpected", response2.getStatusCode(), is(HttpStatus.CREATED));
		
		HRRecruitingOfferDTO offer3 = new HRRecruitingOfferDTO();
		offer3.setJobTitle("Full Stack Developer");
		offer3.setStartDate(new Date());
		offer3.setJobDescription(
				"Full Stack Developer with hands on AngularJS ,Struts");
		ResponseEntity<?> response3 = this.restTemplate.postForEntity(OFFERS_ENDPOINT, offer, String.class);
		assertThat("Status unexpected", response3.getStatusCode(), is(HttpStatus.CREATED));
	}

	private void postApplications() {
		// post offer first before applying
		postOffers();
		HRRecruitingApplicationDTO application1 = new HRRecruitingApplicationDTO();
		application1.setJobTitle("Java Developer");
		application1.setEmailId("candidate1@gmail.com");
		application1.setResumeText("Java developer with 6+ years exp in Spring ,Hinernate ,JPA");
		ResponseEntity<?> response1 = this.restTemplate.postForEntity(APPLICATIONS_ENDPOINT, application1,
				String.class);
		assertThat("Status unexpected", response1.getStatusCode(), is(HttpStatus.CREATED));

		HRRecruitingApplicationDTO application2 = new HRRecruitingApplicationDTO();
		application2.setJobTitle("Java Developer");
		application2.setEmailId("candidate2@gmail.com");
		application2.setResumeText("Java developer with 6+ years exp in Spring ,Hinernate ,JPA");
		ResponseEntity<?> response2 = this.restTemplate.postForEntity(APPLICATIONS_ENDPOINT, application2,
				String.class);
		assertThat("Status unexpected", response2.getStatusCode(), is(HttpStatus.CREATED));
	}

	@Test
	public void testCreateApplication() {
		postApplications();
	}

	@Test
	public void testGetApplicationsByJobTitle() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("jobTitle", "Java Developer");
		URI uri = UriComponentsBuilder.fromUriString(APPLICATIONS_BY_JOB_TITLE).buildAndExpand(params).toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(headers);
		ResponseEntity<?> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Object.class);
		assertThat("Status unexpected", response.getStatusCode(), is(HttpStatus.OK));
		List<HRRecruitingApplicationDTO> applications = (List<HRRecruitingApplicationDTO>) response.getBody();

		assertNotNull(applications);
		assertThat("Applications Count", applications.size(), is(2));
	}

	/*@Test
	public void testGetApplicationByApplicationId() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("jobTitle", "Full Stack Developer");
		params.put("emailId", "candidate5@gmail.com");
		URI uri = UriComponentsBuilder.fromUriString(APPLICATION_BY_APPLICATION_ID_ENDPOINT).buildAndExpand(params)
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(headers);
		ResponseEntity<Application> response = restTemplate.exchange(uri, HttpMethod.GET, entity, Application.class);
		Application application = response.getBody();
		assertNotNull(application);
		assertThat("Status unexpected", response.getStatusCode(), is(HttpStatus.OK));
		assertThat("Application is not matched", application.getEmailId(), is("candidate5@gmail.com"));
	}*/

	@Test
	public void testProgressApplication() {
		HRRecruitingApplicationDTO application = new HRRecruitingApplicationDTO();
		application.setJobTitle("Java Developer");
		application.setEmailId("candidate1@gmail.com");
		application.setApplicationStatus("HIRED");
		ResponseEntity<?> response = this.restTemplate.postForEntity(APPLICATION_PROGRESS_ENDPOINT, application,
				String.class);
		assertThat("Status unexpected", response.getStatusCode(), is(HttpStatus.OK));
		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("jobTitle", "Java Developer");
		params.put("emailId", "candidate1@gmail.com");
		URI uri = UriComponentsBuilder.fromUriString(APPLICATION_BY_APPLICATION_ID_ENDPOINT).buildAndExpand(params)
				.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(headers);
		ResponseEntity<HRRecruitingApplicationDTO> response2 = restTemplate.exchange(uri, HttpMethod.GET, entity, HRRecruitingApplicationDTO.class);
		HRRecruitingApplicationDTO application2 = response2.getBody();
		
		assertThat("Application Status is not changed", application2.getApplicationStatus(), is("HIRED"));
	}

}
