package com.polsastre.jeconomizer.web.rest;

import com.polsastre.jeconomizer.JeconomizerApp;

import com.polsastre.jeconomizer.domain.Percentage;
import com.polsastre.jeconomizer.repository.PercentageRepository;
import com.polsastre.jeconomizer.service.PercentageService;
import com.polsastre.jeconomizer.repository.search.PercentageSearchRepository;
import com.polsastre.jeconomizer.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PercentageResource REST controller.
 *
 * @see PercentageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JeconomizerApp.class)
public class PercentageResourceIntTest {

    private static final Double DEFAULT_DAY_PERCENTAGE = 1D;
    private static final Double UPDATED_DAY_PERCENTAGE = 2D;

    private static final Double DEFAULT_WEEK_PERCENTAGE = 1D;
    private static final Double UPDATED_WEEK_PERCENTAGE = 2D;

    private static final Double DEFAULT_MONTH_PERCENTAGE = 1D;
    private static final Double UPDATED_MONTH_PERCENTAGE = 2D;

    private static final Double DEFAULT_YEAR_PERCENTAGE = 1D;
    private static final Double UPDATED_YEAR_PERCENTAGE = 2D;

    @Autowired
    private PercentageRepository percentageRepository;

    @Autowired
    private PercentageService percentageService;

    @Autowired
    private PercentageSearchRepository percentageSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPercentageMockMvc;

    private Percentage percentage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PercentageResource percentageResource = new PercentageResource(percentageService);
        this.restPercentageMockMvc = MockMvcBuilders.standaloneSetup(percentageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Percentage createEntity(EntityManager em) {
        Percentage percentage = new Percentage()
            .dayPercentage(DEFAULT_DAY_PERCENTAGE)
            .weekPercentage(DEFAULT_WEEK_PERCENTAGE)
            .monthPercentage(DEFAULT_MONTH_PERCENTAGE)
            .yearPercentage(DEFAULT_YEAR_PERCENTAGE);
        return percentage;
    }

    @Before
    public void initTest() {
        percentageSearchRepository.deleteAll();
        percentage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPercentage() throws Exception {
        int databaseSizeBeforeCreate = percentageRepository.findAll().size();

        // Create the Percentage
        restPercentageMockMvc.perform(post("/api/percentages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentage)))
            .andExpect(status().isCreated());

        // Validate the Percentage in the database
        List<Percentage> percentageList = percentageRepository.findAll();
        assertThat(percentageList).hasSize(databaseSizeBeforeCreate + 1);
        Percentage testPercentage = percentageList.get(percentageList.size() - 1);
        assertThat(testPercentage.getDayPercentage()).isEqualTo(DEFAULT_DAY_PERCENTAGE);
        assertThat(testPercentage.getWeekPercentage()).isEqualTo(DEFAULT_WEEK_PERCENTAGE);
        assertThat(testPercentage.getMonthPercentage()).isEqualTo(DEFAULT_MONTH_PERCENTAGE);
        assertThat(testPercentage.getYearPercentage()).isEqualTo(DEFAULT_YEAR_PERCENTAGE);

        // Validate the Percentage in Elasticsearch
        Percentage percentageEs = percentageSearchRepository.findOne(testPercentage.getId());
        assertThat(percentageEs).isEqualToComparingFieldByField(testPercentage);
    }

    @Test
    @Transactional
    public void createPercentageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = percentageRepository.findAll().size();

        // Create the Percentage with an existing ID
        percentage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPercentageMockMvc.perform(post("/api/percentages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentage)))
            .andExpect(status().isBadRequest());

        // Validate the Percentage in the database
        List<Percentage> percentageList = percentageRepository.findAll();
        assertThat(percentageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPercentages() throws Exception {
        // Initialize the database
        percentageRepository.saveAndFlush(percentage);

        // Get all the percentageList
        restPercentageMockMvc.perform(get("/api/percentages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(percentage.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayPercentage").value(hasItem(DEFAULT_DAY_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].weekPercentage").value(hasItem(DEFAULT_WEEK_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].monthPercentage").value(hasItem(DEFAULT_MONTH_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].yearPercentage").value(hasItem(DEFAULT_YEAR_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getPercentage() throws Exception {
        // Initialize the database
        percentageRepository.saveAndFlush(percentage);

        // Get the percentage
        restPercentageMockMvc.perform(get("/api/percentages/{id}", percentage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(percentage.getId().intValue()))
            .andExpect(jsonPath("$.dayPercentage").value(DEFAULT_DAY_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.weekPercentage").value(DEFAULT_WEEK_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.monthPercentage").value(DEFAULT_MONTH_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.yearPercentage").value(DEFAULT_YEAR_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPercentage() throws Exception {
        // Get the percentage
        restPercentageMockMvc.perform(get("/api/percentages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePercentage() throws Exception {
        // Initialize the database
        percentageService.save(percentage);

        int databaseSizeBeforeUpdate = percentageRepository.findAll().size();

        // Update the percentage
        Percentage updatedPercentage = percentageRepository.findOne(percentage.getId());
        updatedPercentage
            .dayPercentage(UPDATED_DAY_PERCENTAGE)
            .weekPercentage(UPDATED_WEEK_PERCENTAGE)
            .monthPercentage(UPDATED_MONTH_PERCENTAGE)
            .yearPercentage(UPDATED_YEAR_PERCENTAGE);

        restPercentageMockMvc.perform(put("/api/percentages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPercentage)))
            .andExpect(status().isOk());

        // Validate the Percentage in the database
        List<Percentage> percentageList = percentageRepository.findAll();
        assertThat(percentageList).hasSize(databaseSizeBeforeUpdate);
        Percentage testPercentage = percentageList.get(percentageList.size() - 1);
        assertThat(testPercentage.getDayPercentage()).isEqualTo(UPDATED_DAY_PERCENTAGE);
        assertThat(testPercentage.getWeekPercentage()).isEqualTo(UPDATED_WEEK_PERCENTAGE);
        assertThat(testPercentage.getMonthPercentage()).isEqualTo(UPDATED_MONTH_PERCENTAGE);
        assertThat(testPercentage.getYearPercentage()).isEqualTo(UPDATED_YEAR_PERCENTAGE);

        // Validate the Percentage in Elasticsearch
        Percentage percentageEs = percentageSearchRepository.findOne(testPercentage.getId());
        assertThat(percentageEs).isEqualToComparingFieldByField(testPercentage);
    }

    @Test
    @Transactional
    public void updateNonExistingPercentage() throws Exception {
        int databaseSizeBeforeUpdate = percentageRepository.findAll().size();

        // Create the Percentage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPercentageMockMvc.perform(put("/api/percentages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(percentage)))
            .andExpect(status().isCreated());

        // Validate the Percentage in the database
        List<Percentage> percentageList = percentageRepository.findAll();
        assertThat(percentageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePercentage() throws Exception {
        // Initialize the database
        percentageService.save(percentage);

        int databaseSizeBeforeDelete = percentageRepository.findAll().size();

        // Get the percentage
        restPercentageMockMvc.perform(delete("/api/percentages/{id}", percentage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean percentageExistsInEs = percentageSearchRepository.exists(percentage.getId());
        assertThat(percentageExistsInEs).isFalse();

        // Validate the database is empty
        List<Percentage> percentageList = percentageRepository.findAll();
        assertThat(percentageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPercentage() throws Exception {
        // Initialize the database
        percentageService.save(percentage);

        // Search the percentage
        restPercentageMockMvc.perform(get("/api/_search/percentages?query=id:" + percentage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(percentage.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayPercentage").value(hasItem(DEFAULT_DAY_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].weekPercentage").value(hasItem(DEFAULT_WEEK_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].monthPercentage").value(hasItem(DEFAULT_MONTH_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].yearPercentage").value(hasItem(DEFAULT_YEAR_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Percentage.class);
        Percentage percentage1 = new Percentage();
        percentage1.setId(1L);
        Percentage percentage2 = new Percentage();
        percentage2.setId(percentage1.getId());
        assertThat(percentage1).isEqualTo(percentage2);
        percentage2.setId(2L);
        assertThat(percentage1).isNotEqualTo(percentage2);
        percentage1.setId(null);
        assertThat(percentage1).isNotEqualTo(percentage2);
    }
}
