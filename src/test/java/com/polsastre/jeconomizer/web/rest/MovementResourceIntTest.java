package com.polsastre.jeconomizer.web.rest;

import com.polsastre.jeconomizer.JeconomizerApp;

import com.polsastre.jeconomizer.domain.Movement;
import com.polsastre.jeconomizer.repository.MovementRepository;
import com.polsastre.jeconomizer.service.MovementService;
import com.polsastre.jeconomizer.repository.search.MovementSearchRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MovementResource REST controller.
 *
 * @see MovementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JeconomizerApp.class)
public class MovementResourceIntTest {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_AMOUNT = 1L;
    private static final Long UPDATED_AMOUNT = 2L;

    private static final byte[] DEFAULT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_BALANCE = 1L;
    private static final Long UPDATED_BALANCE = 2L;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private MovementService movementService;

    @Autowired
    private MovementSearchRepository movementSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMovementMockMvc;

    private Movement movement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MovementResource movementResource = new MovementResource(movementService);
        this.restMovementMockMvc = MockMvcBuilders.standaloneSetup(movementResource)
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
    public static Movement createEntity(EntityManager em) {
        Movement movement = new Movement()
            .startDate(DEFAULT_START_DATE)
            .amount(DEFAULT_AMOUNT)
            .picture(DEFAULT_PICTURE)
            .pictureContentType(DEFAULT_PICTURE_CONTENT_TYPE)
            .balance(DEFAULT_BALANCE);
        return movement;
    }

    @Before
    public void initTest() {
        movementSearchRepository.deleteAll();
        movement = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovement() throws Exception {
        int databaseSizeBeforeCreate = movementRepository.findAll().size();

        // Create the Movement
        restMovementMockMvc.perform(post("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isCreated());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeCreate + 1);
        Movement testMovement = movementList.get(movementList.size() - 1);
        assertThat(testMovement.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMovement.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testMovement.getPicture()).isEqualTo(DEFAULT_PICTURE);
        assertThat(testMovement.getPictureContentType()).isEqualTo(DEFAULT_PICTURE_CONTENT_TYPE);
        assertThat(testMovement.getBalance()).isEqualTo(DEFAULT_BALANCE);

        // Validate the Movement in Elasticsearch
        Movement movementEs = movementSearchRepository.findOne(testMovement.getId());
        assertThat(movementEs).isEqualToComparingFieldByField(testMovement);
    }

    @Test
    @Transactional
    public void createMovementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movementRepository.findAll().size();

        // Create the Movement with an existing ID
        movement.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovementMockMvc.perform(post("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = movementRepository.findAll().size();
        // set the field null
        movement.setStartDate(null);

        // Create the Movement, which fails.

        restMovementMockMvc.perform(post("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = movementRepository.findAll().size();
        // set the field null
        movement.setAmount(null);

        // Create the Movement, which fails.

        restMovementMockMvc.perform(post("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isBadRequest());

        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovements() throws Exception {
        // Initialize the database
        movementRepository.saveAndFlush(movement);

        // Get all the movementList
        restMovementMockMvc.perform(get("/api/movements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movement.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
    }

    @Test
    @Transactional
    public void getMovement() throws Exception {
        // Initialize the database
        movementRepository.saveAndFlush(movement);

        // Get the movement
        restMovementMockMvc.perform(get("/api/movements/{id}", movement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(movement.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.pictureContentType").value(DEFAULT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.picture").value(Base64Utils.encodeToString(DEFAULT_PICTURE)))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMovement() throws Exception {
        // Get the movement
        restMovementMockMvc.perform(get("/api/movements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovement() throws Exception {
        // Initialize the database
        movementService.save(movement);

        int databaseSizeBeforeUpdate = movementRepository.findAll().size();

        // Update the movement
        Movement updatedMovement = movementRepository.findOne(movement.getId());
        updatedMovement
            .startDate(UPDATED_START_DATE)
            .amount(UPDATED_AMOUNT)
            .picture(UPDATED_PICTURE)
            .pictureContentType(UPDATED_PICTURE_CONTENT_TYPE)
            .balance(UPDATED_BALANCE);

        restMovementMockMvc.perform(put("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovement)))
            .andExpect(status().isOk());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeUpdate);
        Movement testMovement = movementList.get(movementList.size() - 1);
        assertThat(testMovement.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMovement.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testMovement.getPicture()).isEqualTo(UPDATED_PICTURE);
        assertThat(testMovement.getPictureContentType()).isEqualTo(UPDATED_PICTURE_CONTENT_TYPE);
        assertThat(testMovement.getBalance()).isEqualTo(UPDATED_BALANCE);

        // Validate the Movement in Elasticsearch
        Movement movementEs = movementSearchRepository.findOne(testMovement.getId());
        assertThat(movementEs).isEqualToComparingFieldByField(testMovement);
    }

    @Test
    @Transactional
    public void updateNonExistingMovement() throws Exception {
        int databaseSizeBeforeUpdate = movementRepository.findAll().size();

        // Create the Movement

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMovementMockMvc.perform(put("/api/movements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(movement)))
            .andExpect(status().isCreated());

        // Validate the Movement in the database
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMovement() throws Exception {
        // Initialize the database
        movementService.save(movement);

        int databaseSizeBeforeDelete = movementRepository.findAll().size();

        // Get the movement
        restMovementMockMvc.perform(delete("/api/movements/{id}", movement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean movementExistsInEs = movementSearchRepository.exists(movement.getId());
        assertThat(movementExistsInEs).isFalse();

        // Validate the database is empty
        List<Movement> movementList = movementRepository.findAll();
        assertThat(movementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMovement() throws Exception {
        // Initialize the database
        movementService.save(movement);

        // Search the movement
        restMovementMockMvc.perform(get("/api/_search/movements?query=id:" + movement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movement.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].pictureContentType").value(hasItem(DEFAULT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].picture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE))))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Movement.class);
        Movement movement1 = new Movement();
        movement1.setId(1L);
        Movement movement2 = new Movement();
        movement2.setId(movement1.getId());
        assertThat(movement1).isEqualTo(movement2);
        movement2.setId(2L);
        assertThat(movement1).isNotEqualTo(movement2);
        movement1.setId(null);
        assertThat(movement1).isNotEqualTo(movement2);
    }
}
