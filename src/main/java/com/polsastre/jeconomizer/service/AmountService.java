package com.polsastre.jeconomizer.service;

import com.polsastre.jeconomizer.domain.Amount;
import java.util.List;

/**
 * Service Interface for managing Amount.
 */
public interface AmountService {

    /**
     * Save a amount.
     *
     * @param amount the entity to save
     * @return the persisted entity
     */
    Amount save(Amount amount);

    /**
     *  Get all the amounts.
     *
     *  @return the list of entities
     */
    List<Amount> findAll();

    /**
     *  Get the "id" amount.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Amount findOne(Long id);

    /**
     *  Delete the "id" amount.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the amount corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Amount> search(String query);
}
