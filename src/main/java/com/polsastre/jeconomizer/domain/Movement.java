package com.polsastre.jeconomizer.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Movement.
 */
@Entity
@Table(name = "movement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "movement")
public class Movement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Column(name = "balance")
    private Long balance;

    @ManyToOne
    private Category categoryId;

    @ManyToOne
    private User userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Movement startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Long getAmount() {
        return amount;
    }

    public Movement amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public byte[] getPicture() {
        return picture;
    }

    public Movement picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public Movement pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public Long getBalance() {
        return balance;
    }

    public Movement balance(Long balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Category getCategoryId() {
        return categoryId;
    }

    public Movement categoryId(Category category) {
        this.categoryId = category;
        return this;
    }

    public void setCategoryId(Category category) {
        this.categoryId = category;
    }

    public User getUserId() {
        return userId;
    }

    public Movement userId(User user) {
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
        Movement movement = (Movement) o;
        if (movement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Movement{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", amount='" + getAmount() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + pictureContentType + "'" +
            ", balance='" + getBalance() + "'" +
            "}";
    }
}
