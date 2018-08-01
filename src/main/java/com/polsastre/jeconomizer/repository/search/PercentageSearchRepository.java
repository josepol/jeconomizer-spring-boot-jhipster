package com.polsastre.jeconomizer.repository.search;

import com.polsastre.jeconomizer.domain.Percentage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Percentage entity.
 */
public interface PercentageSearchRepository extends ElasticsearchRepository<Percentage, Long> {
}
