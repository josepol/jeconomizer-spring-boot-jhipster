package com.polsastre.jeconomizer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Amount.
 */
@Entity
@Table(name = "amount")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "amount")
public class Amount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_income")
    private Long dayIncome;

    @Column(name = "day_expenses")
    private Long dayExpenses;

    @Column(name = "day_balance")
    private Long dayBalance;

    @Column(name = "week_income")
    private Long weekIncome;

    @Column(name = "week_expenses")
    private Long weekExpenses;

    @Column(name = "week_balance")
    private Long weekBalance;

    @Column(name = "month_income")
    private Long monthIncome;

    @Column(name = "month_expenses")
    private Long monthExpenses;

    @Column(name = "month_balance")
    private Long monthBalance;

    @Column(name = "year_income")
    private Long yearIncome;

    @Column(name = "year_expenses")
    private Long yearExpenses;

    @Column(name = "year_balance")
    private Long yearBalance;

    @ManyToOne
    private User userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDayIncome() {
        return dayIncome;
    }

    public Amount dayIncome(Long dayIncome) {
        this.dayIncome = dayIncome;
        return this;
    }

    public void setDayIncome(Long dayIncome) {
        this.dayIncome = dayIncome;
    }

    public Long getDayExpenses() {
        return dayExpenses;
    }

    public Amount dayExpenses(Long dayExpenses) {
        this.dayExpenses = dayExpenses;
        return this;
    }

    public void setDayExpenses(Long dayExpenses) {
        this.dayExpenses = dayExpenses;
    }

    public Long getDayBalance() {
        return dayBalance;
    }

    public Amount dayBalance(Long dayBalance) {
        this.dayBalance = dayBalance;
        return this;
    }

    public void setDayBalance(Long dayBalance) {
        this.dayBalance = dayBalance;
    }

    public Long getWeekIncome() {
        return weekIncome;
    }

    public Amount weekIncome(Long weekIncome) {
        this.weekIncome = weekIncome;
        return this;
    }

    public void setWeekIncome(Long weekIncome) {
        this.weekIncome = weekIncome;
    }

    public Long getWeekExpenses() {
        return weekExpenses;
    }

    public Amount weekExpenses(Long weekExpenses) {
        this.weekExpenses = weekExpenses;
        return this;
    }

    public void setWeekExpenses(Long weekExpenses) {
        this.weekExpenses = weekExpenses;
    }

    public Long getWeekBalance() {
        return weekBalance;
    }

    public Amount weekBalance(Long weekBalance) {
        this.weekBalance = weekBalance;
        return this;
    }

    public void setWeekBalance(Long weekBalance) {
        this.weekBalance = weekBalance;
    }

    public Long getMonthIncome() {
        return monthIncome;
    }

    public Amount monthIncome(Long monthIncome) {
        this.monthIncome = monthIncome;
        return this;
    }

    public void setMonthIncome(Long monthIncome) {
        this.monthIncome = monthIncome;
    }

    public Long getMonthExpenses() {
        return monthExpenses;
    }

    public Amount monthExpenses(Long monthExpenses) {
        this.monthExpenses = monthExpenses;
        return this;
    }

    public void setMonthExpenses(Long monthExpenses) {
        this.monthExpenses = monthExpenses;
    }

    public Long getMonthBalance() {
        return monthBalance;
    }

    public Amount monthBalance(Long monthBalance) {
        this.monthBalance = monthBalance;
        return this;
    }

    public void setMonthBalance(Long monthBalance) {
        this.monthBalance = monthBalance;
    }

    public Long getYearIncome() {
        return yearIncome;
    }

    public Amount yearIncome(Long yearIncome) {
        this.yearIncome = yearIncome;
        return this;
    }

    public void setYearIncome(Long yearIncome) {
        this.yearIncome = yearIncome;
    }

    public Long getYearExpenses() {
        return yearExpenses;
    }

    public Amount yearExpenses(Long yearExpenses) {
        this.yearExpenses = yearExpenses;
        return this;
    }

    public void setYearExpenses(Long yearExpenses) {
        this.yearExpenses = yearExpenses;
    }

    public Long getYearBalance() {
        return yearBalance;
    }

    public Amount yearBalance(Long yearBalance) {
        this.yearBalance = yearBalance;
        return this;
    }

    public void setYearBalance(Long yearBalance) {
        this.yearBalance = yearBalance;
    }

    public User getUserId() {
        return userId;
    }

    public Amount userId(User user) {
        this.userId = user;
        return this;
    }

    public void setUserId(User user) {
        this.userId = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Amount amount = (Amount) o;
        if (amount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Amount{" +
            "id=" + getId() +
            ", dayIncome='" + getDayIncome() + "'" +
            ", dayExpenses='" + getDayExpenses() + "'" +
            ", dayBalance='" + getDayBalance() + "'" +
            ", weekIncome='" + getWeekIncome() + "'" +
            ", weekExpenses='" + getWeekExpenses() + "'" +
            ", weekBalance='" + getWeekBalance() + "'" +
            ", monthIncome='" + getMonthIncome() + "'" +
            ", monthExpenses='" + getMonthExpenses() + "'" +
            ", monthBalance='" + getMonthBalance() + "'" +
            ", yearIncome='" + getYearIncome() + "'" +
            ", yearExpenses='" + getYearExpenses() + "'" +
            ", yearBalance='" + getYearBalance() + "'" +
            "}";
    }
}
