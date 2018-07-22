# hhr-recruiting-service

### Introduction

This is a very simple recruitment service, which allows HR to post job offers, fetch single or all posted offer ,track number of applications per offer , progress status of application through various stages of status such as (APPLIED, INVITED, REJECTED, HIRED).

This service also allows candidates to apply for particular job offer based on job title.

The main use cases of this service are as below.

user has to be able to create a job offer and read a single and list all offers.
candidate has to be able to apply for an offer.
user has to be able to read one and list all applications per offer.
user has to be able to progress the status of an application.
user has to be able to track the number of applications.
status change triggers a notification (*)

### Implementation 
This is service using https://projects.spring.io/spring-boot, using Maven for compiling and building the application.This service uses H2 in memory database for processing offers and applications. Used Spring Data JPA repository for backend operations with H2. For change in any application status this application monitors that using HRRecruitingAppStatusEventListner and log (*) in output.


### List of API's

1. ``POST /offers`` - create new offer
2. ``GET /offers`` - gets all created offers
3. ``GET /offers/{jobTitle}`` - get single offer based on jobTitle
4. ``GET /offers/{jobTitle}/applications/count`` - get number of applications per offer
5. ``POST /applications`` - create a new application for offer
6. ``GET /applications`` - gets all applications.
7. ``GET /applications/{jobTitle}`` - gets all applications for given jobTitle.
8. ``GET /applications/{jobTitle}/{emailId}`` - get single application for given jobTitle and emailId.
9. ``POST /applications/status`` - progress application.

# Specifications


*********************************************************************************************
## Offers

### Create a New Offer

Every Time a new offer created, this endpoint will be called.

Where:

* jobTitle is string defining title for job offer
* startDate is date in yyyy-MM-dd format
* jobDescription is string defining description for job offer

#### Request sample
POST /offers HTTP/1.1
Content-Type: application/json
```
{
    "jobTitle": "Senior Java Developer",
    "startDate": "2018-07-22",
    "jobDescription" : "Ideal candidate should have 8 years of experience in Java/J2EE ,Spring ,Hibernate"
}
```
#### Success response sample

```
201 Created
```
===============================================================================================================================================
### 1. Get All Offers 

Gets all created offers

#### Request sample
GET /offers HTTP/1.1
Content-Type: application/json

#### Success response sample
```
 {
        "jobTitle": "Java Developer",
        "startDate": "2018-07-22",
        "jobDescription": "Ideal Canididate should have 2 years of experience in Java/J2EE ,Spring ,Hibernate",
        "noOfApplications": 0
    },
    {
        "jobTitle": "Senior Java Developer",
        "startDate": "2018-07-22",
        "jobDescription": "Ideal Canididate should have 10 years of experience in Java/J2EE ,Spring ,Hibernate",
        "noOfApplications": 0
    }
```
===============================================================================================================================================
### 2. Get Offer By Job Title 

Get single created offer

#### Request sample
GET /offers/{jobTitle} HTTP/1.1
Content-Type: application/json

#### Success response sample

```
 	{
        "jobTitle": "Java Developer",
        "startDate": "2018-07-22",
        "jobDescription": "Ideal Canididate should have 2 years of experience in Java/J2EE ,Spring ,Hibernate",
        "noOfApplications": 0
    }
```
===============================================================================================================================================
### 3. Get NoOfApplications for offer

Get single created offer

#### Request sample
GET /offers/{jobTitle}/applications/count HTTP/1.1
Content-Type: application/json

#### Success response sample

```
 	{
    "noOfApplications": 1
}
```

*************************************************************************************************
# Applications
Endpoints for this will be used to create track/progress applications

### 4. Create Application

Every Time a new application created , this endpoint will be called.

Where:

* jobTitle is string defining title for job offer which candidate applying
* emailId is string , candidate email address	
* resumeText is string ,defining candidate resume text

#### Request sample
POST /applications HTTP/1.1
Content-Type: application/json
```
{
    "jobTitle": "Java Developer",
    "emailId": "chiragsuthar14@outlook.com",
    "resumeText" : "Java developer with hands on Spring ,Spring boot,Spring Integration and Hibernate"
}
```
#### 5. Success response sample

```
201 Created
```

#### Error response sample

```
204 No Content
```
===============================================================================================================================================
### 6. Get All application 

Gets all received applications for jobTitle

### Request sample
GET /applications/{jobTitle} HTTP/1.1
Content-Type: application/json

### Success response sample

```
 {
        "jobTitle": "Java Developer",
        "emailId": "chiragsuthar14@outlook.com",
        "resumeText": "Java developer with hands on Spring ,Spring boot,Spring Integration and Hibernate",
        "applicationStatus": "APPLIED"
    }
```
#### Error response sample

```
204 No Content
```
===============================================================================================================================================
### 7. Get All applications

Gets all received applications for jobTitle


#### Request sample
GET /applications/{jobTitle} HTTP/1.1
Content-Type: application/json

#### Success response sample

```
 	{
        "jobTitle": "Java Developer",
        "emailId": "chiragsuthar14@outlook.com",
        "resumeText": "Java developer with hands on Spring ,Spring boot,Spring Integration and Hibernate",
        "applicationStatus": "APPLIED"
    }
    
    {
        "jobTitle": "Java Developer",
        "emailId": "Candidate2@outlook.com",
        "resumeText": "Java developer ",
        "applicationStatus": "REJECTED"
    }
```
#### Error response sample

```
204 No Content
```
===============================================================================================================================================
### 8. Get Single application 

Gets single received applications for jobTitle

#### Request sample
GET /applications/{jobTitle}/{emailId} HTTP/1.1
Content-Type: application/json

#### Success response sample

```
 	{
        "jobTitle": "Java Developer",
        "emailId": "chiragsuthar14@outlook.com",
        "resumeText": "Java developer with hands on Spring ,Spring boot,Spring Integration and Hibernate",
        "applicationStatus": "APPLIED"
    }
```
#### Error response sample

```
204 No Content
```
===============================================================================================================================================
### 9. Progress application

Every Time if there is a change in application status this endpoint will be called

Where:

* jobTitle is string defining title for job offer which candidate applied
* emailId is string , candidate email address	
* applicationStatus is string (enum type) ,defining candidate new status and must be one of (APPLIED, INVITED, REJECTED, HIRED)

#### Request sample
POST /applications/status HTTP/1.1
Content-Type: application/json
```
{
    "jobTitle": "Java Developer",
    "emailId": "chiragsuthar14@outlook.com",
    "applicationStatus" : "HIRED"
}
```
#### Success response sample

```
201 Created
```

#### Error response sample

```
304 Not modified
```
===============================================================================================================================================
	    
### Build
You can build using:

  $ mvn clean install

### Run
You can run using:

  $ mvn spring-boot:run

### Junit
 You can also run junit test case using :
 $ mvn test 

