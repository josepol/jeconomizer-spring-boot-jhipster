package com.polsastre.jeconomizer.web.rest;

import com.polsastre.jeconomizer.JeconomizerApp;

import com.polsastre.jeconomizer.domain.Amount;
import com.polsastre.jeconomizer.repository.AmountRepository;
import com.polsastre.jeconomizer.service.AmountService;
import com.polsastre.jeconomizer.repository.search.AmountSearchRepository;
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
 * Test class for the AmountResource REST controller.
 *
 * @see AmountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JeconomizerApp.class)
public class AmountResourceIntTest {

    private static final Long DEFAULT_DAY_INCOME = 1L;
    private static final Long UPDATED_DAY_INCOME = 2L;

    private static final Long DEFAULT_DAY_EXPENSES = 1L;
    private static final Long UPDATED_DAY_EXPENSES = 2L;

    private static final Long DEFAULT_DAY_BALANCE = 1L;
    private static final Long UPDATED_DAY_BALANCE = 2L;

    private static final Long DEFAULT_WEEK_INCOME = 1L;
    private static final Long UPDATED_WEEK_INCOME = 2L;

    private static final Long DEFAULT_WEEK_EXPENSES = 1L;
    private static final Long UPDATED_WEEK_EXPENSES = 2L;

    private static final Long DEFAULT_WEEK_BALANCE = 1L;
    private static final Long UPDATED_WEEK_BALANCE = 2L;

    private static final Long DEFAULT_MONTH_INCOME = 1L;
    private static final Long UPDATED_MONTH_INCOME = 2L;

    private static final Long DEFAULT_MONTH_EXPENSES = 1L;
    private static final Long UPDATED_MONTH_EXPENSES = 2L;

    private static final Long DEFAULT_MONTH_BALANCE = 1L;
    private static final Long UPDATED_MONTH_BALANCE = 2L;

    private static final Long DEFAULT_YEAR_INCOME = 1L;
    private static final Long UPDATED_YEAR_INCOME = 2L;

    private static final Long DEFAULT_YEAR_EXPENSES = 1L;
    private static final Long UPDATED_YEAR_EXPENSES = 2L;

    private static final Long DEFAULT_YEAR_BALANCE = 1L;
    private static final Long UPDATED_YEAR_BALANCE = 2L;

    @Autowired
    private AmountRepository amountRepository;

    @Autowired
    private AmountService amountService;

    @Autowired
    private AmountSearchRepository amountSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAmountMockMvc;

    private Amount amount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmountResource amountResource = new AmountResource(amountService);
        this.restAmountMockMvc = MockMvcBuilders.standaloneSetup(amountResource)
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
    public static Amount createEntity(EntityManager em) {
        Amount amount = new Amount()
            .dayIncome(DEFAULT_DAY_INCOME)
            .dayExpenses(DEFAULT_DAY_EXPENSES)
            .dayBalance(DEFAULT_DAY_BALANCE)
            .weekIncome(DEFAULT_WEEK_INCOME)
            .weekExpenses(DEFAULT_WEEK_EXPENSES)
            .weekBalance(DEFAULT_WEEK_BALANCE)
            .monthIncome(DEFAULT_MONTH_INCOME)
            .monthExpenses(DEFAULT_MONTH_EXPENSES)
            .monthBalance(DEFAULT_MONTH_BALANCE)
            .yearIncome(DEFAULT_YEAR_INCOME)
            .yearExpenses(DEFAULT_YEAR_EXPENSES)
            .yearBalance(DEFAULT_YEAR_BALANCE);
        return amount;
    }

    @Before
    public void initTest() {
        amountSearchRepository.deleteAll();
        amount = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmount() throws Exception {
        int databaseSizeBeforeCreate = amountRepository.findAll().size();

        // Create the Amount
        restAmountMockMvc.perform(post("/api/amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isCreated());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeCreate + 1);
        Amount testAmount = amountList.get(amountList.size() - 1);
        assertThat(testAmount.getDayIncome()).isEqualTo(DEFAULT_DAY_INCOME);
        assertThat(testAmount.getDayExpenses()).isEqualTo(DEFAULT_DAY_EXPENSES);
        assertThat(testAmount.getDayBalance()).isEqualTo(DEFAULT_DAY_BALANCE);
        assertThat(testAmount.getWeekIncome()).isEqualTo(DEFAULT_WEEK_INCOME);
        assertThat(testAmount.getWeekExpenses()).isEqualTo(DEFAULT_WEEK_EXPENSES);
        assertThat(testAmount.getWeekBalance()).isEqualTo(DEFAULT_WEEK_BALANCE);
        assertThat(testAmount.getMonthIncome()).isEqualTo(DEFAULT_MONTH_INCOME);
        assertThat(testAmount.getMonthExpenses()).isEqualTo(DEFAULT_MONTH_EXPENSES);
        assertThat(testAmount.getMonthBalance()).isEqualTo(DEFAULT_MONTH_BALANCE);
        assertThat(testAmount.getYearIncome()).isEqualTo(DEFAULT_YEAR_INCOME);
        assertThat(testAmount.getYearExpenses()).isEqualTo(DEFAULT_YEAR_EXPENSES);
        assertThat(testAmount.getYearBalance()).isEqualTo(DEFAULT_YEAR_BALANCE);

        // Validate the Amount in Elasticsearch
        Amount amountEs = amountSearchRepository.findOne(testAmount.getId());
        assertThat(amountEs).isEqualToComparingFieldByField(testAmount);
    }

    @Test
    @Transactional
    public void createAmountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amountRepository.findAll().size();

        // Create the Amount with an existing ID
        amount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmountMockMvc.perform(post("/api/amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isBadRequest());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAmounts() throws Exception {
        // Initialize the database
        amountRepository.saveAndFlush(amount);

        // Get all the amountList
        restAmountMockMvc.perform(get("/api/amounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amount.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayIncome").value(hasItem(DEFAULT_DAY_INCOME.intValue())))
            .andExpect(jsonPath("$.[*].dayExpenses").value(hasItem(DEFAULT_DAY_EXPENSES.intValue())))
            .andExpect(jsonPath("$.[*].dayBalance").value(hasItem(DEFAULT_DAY_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].weekIncome").value(hasItem(DEFAULT_WEEK_INCOME.intValue())))
            .andExpect(jsonPath("$.[*].weekExpenses").value(hasItem(DEFAULT_WEEK_EXPENSES.intValue())))
            .andExpect(jsonPath("$.[*].weekBalance").value(hasItem(DEFAULT_WEEK_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].monthIncome").value(hasItem(DEFAULT_MONTH_INCOME.intValue())))
            .andExpect(jsonPath("$.[*].monthExpenses").value(hasItem(DEFAULT_MONTH_EXPENSES.intValue())))
            .andExpect(jsonPath("$.[*].monthBalance").value(hasItem(DEFAULT_MONTH_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].yearIncome").value(hasItem(DEFAULT_YEAR_INCOME.intValue())))
            .andExpect(jsonPath("$.[*].yearExpenses").value(hasItem(DEFAULT_YEAR_EXPENSES.intValue())))
            .andExpect(jsonPath("$.[*].yearBalance").value(hasItem(DEFAULT_YEAR_BALANCE.intValue())));
    }

    @Test
    @Transactional
    public void getAmount() throws Exception {
        // Initialize the database
        amountRepository.saveAndFlush(amount);

        // Get the amount
        restAmountMockMvc.perform(get("/api/amounts/{id}", amount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amount.getId().intValue()))
            .andExpect(jsonPath("$.dayIncome").value(DEFAULT_DAY_INCOME.intValue()))
            .andExpect(jsonPath("$.dayExpenses").value(DEFAULT_DAY_EXPENSES.intValue()))
            .andExpect(jsonPath("$.dayBalance").value(DEFAULT_DAY_BALANCE.intValue()))
            .andExpect(jsonPath("$.weekIncome").value(DEFAULT_WEEK_INCOME.intValue()))
            .andExpect(jsonPath("$.weekExpenses").value(DEFAULT_WEEK_EXPENSES.intValue()))
            .andExpect(jsonPath("$.weekBalance").value(DEFAULT_WEEK_BALANCE.intValue()))
            .andExpect(jsonPath("$.monthIncome").value(DEFAULT_MONTH_INCOME.intValue()))
            .andExpect(jsonPath("$.monthExpenses").value(DEFAULT_MONTH_EXPENSES.intValue()))
            .andExpect(jsonPath("$.monthBalance").value(DEFAULT_MONTH_BALANCE.intValue()))
            .andExpect(jsonPath("$.yearIncome").value(DEFAULT_YEAR_INCOME.intValue()))
            .andExpect(jsonPath("$.yearExpenses").value(DEFAULT_YEAR_EXPENSES.intValue()))
            .andExpect(jsonPath("$.yearBalance").value(DEFAULT_YEAR_BALANCE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAmount() throws Exception {
        // Get the amount
        restAmountMockMvc.perform(get("/api/amounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmount() throws Exception {
        // Initialize the database
        amountService.save(amount);

        int databaseSizeBeforeUpdate = amountRepository.findAll().size();

        // Update the amount
        Amount updatedAmount = amountRepository.findOne(amount.getId());
        updatedAmount
            .dayIncome(UPDATED_DAY_INCOME)
            .dayExpenses(UPDATED_DAY_EXPENSES)
            .dayBalance(UPDATED_DAY_BALANCE)
            .weekIncome(UPDATED_WEEK_INCOME)
            .weekExpenses(UPDATED_WEEK_EXPENSES)
            .weekBalance(UPDATED_WEEK_BALANCE)
            .monthIncome(UPDATED_MONTH_INCOME)
            .monthExpenses(UPDATED_MONTH_EXPENSES)
            .monthBalance(UPDATED_MONTH_BALANCE)
            .yearIncome(UPDATED_YEAR_INCOME)
            .yearExpenses(UPDATED_YEAR_EXPENSES)
            .yearBalance(UPDATED_YEAR_BALANCE);

        restAmountMockMvc.perform(put("/api/amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmount)))
            .andExpect(status().isOk());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeUpdate);
        Amount testAmount = amountList.get(amountList.size() - 1);
        assertThat(testAmount.getDayIncome()).isEqualTo(UPDATED_DAY_INCOME);
        assertThat(testAmount.getDayExpenses()).isEqualTo(UPDATED_DAY_EXPENSES);
        assertThat(testAmount.getDayBalance()).isEqualTo(UPDATED_DAY_BALANCE);
        assertThat(testAmount.getWeekIncome()).isEqualTo(UPDATED_WEEK_INCOME);
        assertThat(testAmount.getWeekExpenses()).isEqualTo(UPDATED_WEEK_EXPENSES);
        assertThat(testAmount.getWeekBalance()).isEqualTo(UPDATED_WEEK_BALANCE);
        assertThat(testAmount.getMonthIncome()).isEqualTo(UPDATED_MONTH_INCOME);
        assertThat(testAmount.getMonthExpenses()).isEqualTo(UPDATED_MONTH_EXPENSES);
        assertThat(testAmount.getMonthBalance()).isEqualTo(UPDATED_MONTH_BALANCE);
        assertThat(testAmount.getYearIncome()).isEqualTo(UPDATED_YEAR_INCOME);
        assertThat(testAmount.getYearExpenses()).isEqualTo(UPDATED_YEAR_EXPENSES);
        assertThat(testAmount.getYearBalance()).isEqualTo(UPDATED_YEAR_BALANCE);

        // Validate the Amount in Elasticsearch
        Amount amountEs = amountSearchRepository.findOne(testAmount.getId());
        assertThat(amountEs).isEqualToComparingFieldByField(testAmount);
    }

    @Test
    @Transactional
    public void updateNonExistingAmount() throws Exception {
        int databaseSizeBeforeUpdate = amountRepository.findAll().size();

        // Create the Amount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAmountMockMvc.perform(put("/api/amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amount)))
            .andExpect(status().isCreated());

        // Validate the Amount in the database
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAmount() throws Exception {
        // Initialize the database
        amountService.save(amount);

        int databaseSizeBeforeDelete = amountRepository.findAll().size();

        // Get the amount
        restAmountMockMvc.perform(delete("/api/amounts/{id}", amount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean amountExistsInEs = amountSearchRepository.exists(amount.getId());
        assertThat(amountExistsInEs).isFalse();

        // Validate the database is empty
        List<Amount> amountList = amountRepository.findAll();
        assertThat(amountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAmount() throws Exception {
        // Initialize the database
        amountService.save(amount);

        // Search the amount
        restAmountMockMvc.perform(get("/api/_search/amounts?query=id:" + amount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amount.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayIncome").value(hasItem(DEFAULT_DAY_INCOME.intValue())))
            .andExpect(jsonPath("$.[*].dayExpenses").value(hasItem(DEFAULT_DAY_EXPENSES.intValue())))
            .andExpect(jsonPath("$.[*].dayBalance").value(hasItem(DEFAULT_DAY_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].weekIncome").value(hasItem(DEFAULT_WEEK_INCOME.intValue())))
            .andExpect(jsonPath("$.[*].weekExpenses").value(hasItem(DEFAULT_WEEK_EXPENSES.intValue())))
            .andExpect(jsonPath("$.[*].weekBalance").value(hasItem(DEFAULT_WEEK_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].monthIncome").value(hasItem(DEFAULT_MONTH_INCOME.intValue())))
            .andExpect(jsonPath("$.[*].monthExpenses").value(hasItem(DEFAULT_MONTH_EXPENSES.intValue())))
            .andExpect(jsonPath("$.[*].monthBalance").value(hasItem(DEFAULT_MONTH_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].yearIncome").value(hasItem(DEFAULT_YEAR_INCOME.intValue())))
            .andExpect(jsonPath("$.[*].yearExpenses").value(hasItem(DEFAULT_YEAR_EXPENSES.intValue())))
            .andExpect(jsonPath("$.[*].yearBalance").value(hasItem(DEFAULT_YEAR_BALANCE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Amount.class);
        Amount amount1 = new Amount();
        amount1.setId(1L);
        Amount amount2 = new Amount();
        amount2.setId(amount1.getId());
        assertThat(amount1).isEqualTo(amount2);
        amount2.setId(2L);
        assertThat(amount1).isNotEqualTo(amount2);
        amount1.setId(null);
        assertThat(amount1).isNotEqualTo(amount2);
    }
}
