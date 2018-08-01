package com.polsastre.jeconomizer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.polsastre.jeconomizer.domain.Percentage;
import com.polsastre.jeconomizer.service.PercentageService;
import com.polsastre.jeconomizer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Percentage.
 */
@RestController
@RequestMapping("/api")
public class PercentageResource {

    private final Logger log = LoggerFactory.getLogger(PercentageResource.class);

    private static final String ENTITY_NAME = "percentage";

    private final PercentageService percentageService;

    public PercentageResource(PercentageService percentageService) {
        this.percentageService = percentageService;
    }

    /**
     * POST  /percentages : Create a new percentage.
     *
     * @param percentage the percentage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new percentage, or with status 400 (Bad Request) if the percentage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/percentages")
    @Timed
    public ResponseEntity<Percentage> createPercentage(@RequestBody Percentage percentage) throws URISyntaxException {
        log.debug("REST request to save Percentage : {}", percentage);
        if (percentage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new percentage cannot already have an ID")).body(null);
        }
        Percentage result = percentageService.save(percentage);
        return ResponseEntity.created(new URI("/api/percentages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /percentages : Updates an existing percentage.
     *
     * @param percentage the percentage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated percentage,
     * or with status 400 (Bad Request) if the percentage is not valid,
     * or with status 500 (Internal Server Error) if the percentage couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/percentages")
    @Timed
    public ResponseEntity<Percentage> updatePercentage(@RequestBody Percentage percentage) throws URISyntaxException {
        log.debug("REST request to update Percentage : {}", percentage);
        if (percentage.getId() == null) {
            return createPercentage(percentage);
        }
        Percentage result = percentageService.save(percentage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, percentage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /percentages : get all the percentages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of percentages in body
     */
    @GetMapping("/percentages")
    @Timed
    public List<Percentage> getAllPercentages() {
        log.debug("REST request to get all Percentages");
        return percentageService.findAll();
        }

    /**
     * GET  /percentages/:id : get the "id" percentage.
     *
     * @param id the id of the percentage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the percentage, or with status 404 (Not Found)
     */
    @GetMapping("/percentages/{id}")
    @Timed
    public ResponseEntity<Percentage> getPercentage(@PathVariable Long id) {
        log.debug("REST request to get Percentage : {}", id);
        Percentage percentage = percentageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(percentage));
    }

    /**
     * DELETE  /percentages/:id : delete the "id" percentage.
     *
     * @param id the id of the percentage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/percentages/{id}")
    @Timed
    public ResponseEntity<Void> deletePercentage(@PathVariable Long id) {
        log.debug("REST request to delete Percentage : {}", id);
        percentageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/percentages?query=:query : search for the percentage corresponding
     * to the query.
     *
     * @param query the query of the percentage search
     * @return the result of the search
     */
    @GetMapping("/_search/percentages")
    @Timed
    public List<Percentage> searchPercentages(@RequestParam String query) {
        log.debug("REST request to search Percentages for query {}", query);
        return percentageService.search(query);
    }

}
