package com.polsastre.jeconomizer.repository.search;

import com.polsastre.jeconomizer.domain.Category;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Category entity.
 */
public interface CategorySearchRepository extends ElasticsearchRepository<Category, Long> {
}
