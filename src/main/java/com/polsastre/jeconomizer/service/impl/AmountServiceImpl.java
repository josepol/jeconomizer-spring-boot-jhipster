package com.polsastre.jeconomizer.service.impl;

import com.polsastre.jeconomizer.service.AmountService;
import com.polsastre.jeconomizer.domain.Amount;
import com.polsastre.jeconomizer.repository.AmountRepository;
import com.polsastre.jeconomizer.repository.search.AmountSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Amount.
 */
@Service
@Transactional
public class AmountServiceImpl implements AmountService{

    private final Logger log = LoggerFactory.getLogger(AmountServiceImpl.class);

    private final AmountRepository amountRepository;

    private final AmountSearchRepository amountSearchRepository;

    public AmountServiceImpl(AmountRepository amountRepository, AmountSearchRepository amountSearchRepository) {
        this.amountRepository = amountRepository;
        this.amountSearchRepository = amountSearchRepository;
    }

    /**
     * Save a amount.
     *
     * @param amount the entity to save
     * @return the persisted entity
     */
    @Override
    public Amount save(Amount amount) {
        log.debug("Request to save Amount : {}", amount);
        Amount result = amountRepository.save(amount);
        amountSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the amounts.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Amount> findAll() {
        log.debug("Request to get all Amounts");
        return amountRepository.findAll();
    }

    /**
     *  Get one amount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Amount findOne(Long id) {
        log.debug("Request to get Amount : {}", id);
        return amountRepository.findOne(id);
    }

    /**
     *  Delete the  amount by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Amount : {}", id);
        amountRepository.delete(id);
        amountSearchRepository.delete(id);
    }

    /**
     * Search for the amount corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Amount> search(String query) {
        log.debug("Request to search Amounts for query {}", query);
        return StreamSupport
            .stream(amountSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
