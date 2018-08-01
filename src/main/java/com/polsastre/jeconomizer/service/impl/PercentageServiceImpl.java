package com.polsastre.jeconomizer.service.impl;

import com.polsastre.jeconomizer.service.PercentageService;
import com.polsastre.jeconomizer.domain.Percentage;
import com.polsastre.jeconomizer.repository.PercentageRepository;
import com.polsastre.jeconomizer.repository.search.PercentageSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Percentage.
 */
@Service
@Transactional
public class PercentageServiceImpl implements PercentageService{

    private final Logger log = LoggerFactory.getLogger(PercentageServiceImpl.class);

    private final PercentageRepository percentageRepository;

    private final PercentageSearchRepository percentageSearchRepository;

    public PercentageServiceImpl(PercentageRepository percentageRepository, PercentageSearchRepository percentageSearchRepository) {
        this.percentageRepository = percentageRepository;
        this.percentageSearchRepository = percentageSearchRepository;
    }

    /**
     * Save a percentage.
     *
     * @param percentage the entity to save
     * @return the persisted entity
     */
    @Override
    public Percentage save(Percentage percentage) {
        log.debug("Request to save Percentage : {}", percentage);
        Percentage result = percentageRepository.save(percentage);
        percentageSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the percentages.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Percentage> findAll() {
        log.debug("Request to get all Percentages");
        return percentageRepository.findAll();
    }

    /**
     *  Get one percentage by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Percentage findOne(Long id) {
        log.debug("Request to get Percentage : {}", id);
        return percentageRepository.findOne(id);
    }

    /**
     *  Delete the  percentage by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Percentage : {}", id);
        percentageRepository.delete(id);
        percentageSearchRepository.delete(id);
    }

    /**
     * Search for the percentage corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Percentage> search(String query) {
        log.debug("Request to search Percentages for query {}", query);
        return StreamSupport
            .stream(percentageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
