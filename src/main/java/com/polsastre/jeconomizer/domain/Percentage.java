package com.polsastre.jeconomizer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Percentage.
 */
@Entity
@Table(name = "percentage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "percentage")
public class Percentage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_percentage")
    private Double dayPercentage;

    @Column(name = "week_percentage")
    private Double weekPercentage;

    @Column(name = "month_percentage")
    private Double monthPercentage;

    @Column(name = "year_percentage")
    private Double yearPercentage;

    @OneToOne
    @JoinColumn(unique = true)
    private Category categoryId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDayPercentage() {
        return dayPercentage;
    }

    public Percentage dayPercentage(Double dayPercentage) {
        this.dayPercentage = dayPercentage;
        return this;
    }

    public void setDayPercentage(Double dayPercentage) {
        this.dayPercentage = dayPercentage;
    }

    public Double getWeekPercentage() {
        return weekPercentage;
    }

    public Percentage weekPercentage(Double weekPercentage) {
        this.weekPercentage = weekPercentage;
        return this;
    }

    public void setWeekPercentage(Double weekPercentage) {
        this.weekPercentage = weekPercentage;
    }

    public Double getMonthPercentage() {
        return monthPercentage;
    }

    public Percentage monthPercentage(Double monthPercentage) {
        this.monthPercentage = monthPercentage;
        return this;
    }

    public void setMonthPercentage(Double monthPercentage) {
        this.monthPercentage = monthPercentage;
    }

    public Double getYearPercentage() {
        return yearPercentage;
    }

    public Percentage yearPercentage(Double yearPercentage) {
        this.yearPercentage = yearPercentage;
        return this;
    }

    public void setYearPercentage(Double yearPercentage) {
        this.yearPercentage = yearPercentage;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public Percentage categoryId(Category category) {
        this.categoryId = category;
        return this;
    }

    public void setCategoryId(Category category) {
        this.categoryId = category;
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
        Percentage percentage = (Percentage) o;
        if (percentage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), percentage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Percentage{" +
            "id=" + getId() +
            ", dayPercentage='" + getDayPercentage() + "'" +
            ", weekPercentage='" + getWeekPercentage() + "'" +
            ", monthPercentage='" + getMonthPercentage() + "'" +
            ", yearPercentage='" + getYearPercentage() + "'" +
            "}";
    }
}
