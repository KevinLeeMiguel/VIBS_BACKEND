package com.vibs_backend.vibs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PaymentTransaction implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Column(updatable = false)
    private String id;
    @ManyToOne
    private Subscription subscription;
    private Boolean deletedStatus = false;
    @Column(nullable = false, updatable = false)
    private Date doneAt = new Date();
    @JsonIgnore
    private String doneBy = "";
    @JsonIgnore
    private Date lastUpdatedAt; // = null;
    @JsonIgnore
    private String lastUpdatedBy = "";
    

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Subscription return the subscription
     */
    public Subscription getSubscription() {
        return subscription;
    }

    /**
     * @param subscription the subscription to set
     */
    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    /**
     * @return Boolean return the deletedStatus
     */
    public Boolean isDeletedStatus() {
        return deletedStatus;
    }

    /**
     * @param deletedStatus the deletedStatus to set
     */
    public void setDeletedStatus(Boolean deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    /**
     * @return Date return the doneAt
     */
    public Date getDoneAt() {
        return doneAt;
    }

    /**
     * @param doneAt the doneAt to set
     */
    public void setDoneAt(Date doneAt) {
        this.doneAt = doneAt;
    }

    /**
     * @return String return the doneBy
     */
    public String getDoneBy() {
        return doneBy;
    }

    /**
     * @param doneBy the doneBy to set
     */
    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    /**
     * @return Date return the lastUpdatedAt
     */
    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    /**
     * @param lastUpdatedAt the lastUpdatedAt to set
     */
    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    /**
     * @return String return the lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the lastUpdatedBy to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Boolean getDeletedStatus() {
        return deletedStatus;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}