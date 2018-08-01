package com.polsastre.jeconomizer.service;

import com.polsastre.jeconomizer.domain.Movement;
import java.util.List;

/**
 * Service Interface for managing Movement.
 */
public interface MovementService {

    /**
     * Save a movement.
     *
     * @param movement the entity to save
     * @return the persisted entity
     */
    Movement save(Movement movement);

    /**
     *  Get all the movements.
     *
     *  @return the list of entities
     */
    List<Movement> findAll();

    /**
     *  Get the "id" movement.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Movement findOne(Long id);

    /**
     *  Delete the "id" movement.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the movement corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Movement> search(String query);
}
