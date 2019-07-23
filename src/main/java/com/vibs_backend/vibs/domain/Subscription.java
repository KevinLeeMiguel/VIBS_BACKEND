package com.vibs_backend.vibs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(updatable = false)
    private String id = UUID.randomUUID().toString();
    private long days;
    private Date startDate;
    private Date endDate;
    private boolean active;
    private double price;
    private boolean paymentStatus = false;
    @Column(columnDefinition = "TEXT")
    private String qrCode;
    // @JsonIgnore
    @OneToOne
    private AutoUsage autoUsage;
    // @JsonIgnore
    @OneToOne
    private AutoType autoType;
    // @JsonIgnore
    @OneToOne
    private InsuranceType type;
    private String companyReferenceId;
    private String companyName;
    // @JsonIgnore
    @OneToOne
    private Vehicle vehicle;
    private Boolean deletedStatus = false;
    @Column(nullable = false, updatable = false)
    private Date doneAt = new Date();
    @JsonIgnore
    private String doneBy = "";
    @JsonIgnore
    private Date lastUpdatedAt; // = null;
    @JsonIgnore
    private String lastUpdatedBy = "";
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionStatus status) {
        this.status = status;
    }

    public Boolean getDeletedStatus() {
        return deletedStatus;
    }

    public void setDeletedStatus(Boolean deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public AutoUsage getAutoUsage() {
        return autoUsage;
    }

    public void setAutoUsage(AutoUsage autoUsage) {
        this.autoUsage = autoUsage;
    }

    public AutoType getAutoType() {
        return autoType;
    }

    public void setAutoType(AutoType autoType) {
        this.autoType = autoType;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public InsuranceType getType() {
        return type;
    }

    public void setType(InsuranceType type) {
        this.type = type;
    }

    public String getCompanyReferenceId() {
        return companyReferenceId;
    }

    public void setCompanyReferenceId(String companyReferenceId) {
        this.companyReferenceId = companyReferenceId;
    }

}