/**
 * 
 */
package com.hhr.group.recruiting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hhr.group.recruiting.entity.OfferCatalog;

/**
 * @author chirag suthar
 * @param <T>
 * 
 * Repository for all offers processing related operations 
 *
 */
@Repository
public interface OfferCatalogRepository extends JpaRepository<OfferCatalog, String> {


}
