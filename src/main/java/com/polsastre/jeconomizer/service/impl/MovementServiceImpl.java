package com.polsastre.jeconomizer.service.impl;

import com.polsastre.jeconomizer.service.MovementService;
import com.polsastre.jeconomizer.domain.Movement;
import com.polsastre.jeconomizer.repository.MovementRepository;
import com.polsastre.jeconomizer.repository.search.MovementSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Movement.
 */
@Service
@Transactional
public class MovementServiceImpl implements MovementService{

    private final Logger log = LoggerFactory.getLogger(MovementServiceImpl.class);

    private final MovementRepository movementRepository;

    private final MovementSearchRepository movementSearchRepository;

    public MovementServiceImpl(MovementRepository movementRepository, MovementSearchRepository movementSearchRepository) {
        this.movementRepository = movementRepository;
        this.movementSearchRepository = movementSearchRepository;
    }

    /**
     * Save a movement.
     *
     * @param movement the entity to save
     * @return the persisted entity
     */
    @Override
    public Movement save(Movement movement) {
        log.debug("Request to save Movement : {}", movement);
        Movement result = movementRepository.save(movement);
        movementSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the movements.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Movement> findAll() {
        log.debug("Request to get all Movements");
        return movementRepository.findAll();
    }

    /**
     *  Get one movement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Movement findOne(Long id) {
        log.debug("Request to get Movement : {}", id);
        return movementRepository.findOne(id);
    }

    /**
     *  Delete the  movement by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Movement : {}", id);
        movementRepository.delete(id);
        movementSearchRepository.delete(id);
    }

    /**
     * Search for the movement corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Movement> search(String query) {
        log.debug("Request to search Movements for query {}", query);
        return StreamSupport
            .stream(movementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
