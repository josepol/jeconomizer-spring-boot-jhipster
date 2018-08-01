package com.polsastre.jeconomizer.repository.search;

import com.polsastre.jeconomizer.domain.Movement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Movement entity.
 */
public interface MovementSearchRepository extends ElasticsearchRepository<Movement, Long> {
}
