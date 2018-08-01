package com.polsastre.jeconomizer.service;

import com.polsastre.jeconomizer.domain.Percentage;
import java.util.List;

/**
 * Service Interface for managing Percentage.
 */
public interface PercentageService {

    /**
     * Save a percentage.
     *
     * @param percentage the entity to save
     * @return the persisted entity
     */
    Percentage save(Percentage percentage);

    /**
     *  Get all the percentages.
     *
     *  @return the list of entities
     */
    List<Percentage> findAll();

    /**
     *  Get the "id" percentage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Percentage findOne(Long id);

    /**
     *  Delete the "id" percentage.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the percentage corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Percentage> search(String query);
}
