package com.hhr.group.recruiting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hhr.group.recruiting.entity.ApplicationDetail;
import com.hhr.group.recruiting.entity.ApplicationId;

/**
 * @author chirag suthar
 * 
 * Repository for all application processing related operations 
 *
 */
@Repository
public interface HRRecruitingApplicationDetailRepository extends JpaRepository<ApplicationDetail, ApplicationId> {

	/**
	 * @param jobTitle
	 * @return
	 */
	@Query("SELECT ad FROM ApplicationDetail ad WHERE ad.applicationId.jobTitle = :jobTitle")
	public Optional<List<ApplicationDetail>> findAllApplicationsByJobTitle(@Param("jobTitle") String jobTitle);

	/**
	 * @param jobTitle
	 * @return
	 */
	@Query("SELECT COUNT(*) FROM ApplicationDetail ad WHERE ad.applicationId.jobTitle = :jobTitle")
	public int getApplicationCountPerjobTitle(@Param("jobTitle") String jobTitle);

}
