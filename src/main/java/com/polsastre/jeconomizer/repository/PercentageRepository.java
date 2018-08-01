package com.polsastre.jeconomizer.repository;

import com.polsastre.jeconomizer.domain.Percentage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Percentage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PercentageRepository extends JpaRepository<Percentage, Long> {

}
