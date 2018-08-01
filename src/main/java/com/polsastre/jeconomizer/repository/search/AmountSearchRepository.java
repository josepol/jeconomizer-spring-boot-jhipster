package com.polsastre.jeconomizer.repository.search;

import com.polsastre.jeconomizer.domain.Amount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Amount entity.
 */
public interface AmountSearchRepository extends ElasticsearchRepository<Amount, Long> {
}
