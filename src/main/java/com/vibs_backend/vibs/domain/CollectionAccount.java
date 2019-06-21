package com.vibs_backend.vibs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CollectionAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id = UUID.randomUUID().toString();
    private double balance = 0.0;
    @OneToOne
    private InsuranceCompany company;
    private Boolean deletedStatus = false;
    @Column(nullable = false, updatable = false)
    private Date doneAt = new Date();
    @JsonIgnore
    private String doneBy = "";
    @JsonIgnore
    private Date lastUpdatedAt; // = null;
    @JsonIgnore
    private String lastUpdatedBy = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public InsuranceCompany getCompany() {
        return company;
    }

    public void setCompany(InsuranceCompany company) {
        this.company = company;
    }

    public Boolean getDeletedStatus() {
        return deletedStatus;
    }

    public void setDeletedStatus(Boolean deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public Date getDoneAt() {
        return doneAt;
    }

    public void setDoneAt(Date doneAt) {
        this.doneAt = doneAt;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    

}