package com.vibs_backend.vibs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id = UUID.randomUUID().toString();
    private AutoType vehicleType;
    private AutoUsage vehicleUsage;
    private long days;
    private Date startDate;
    private Date endDate;
    private double price;
    private AutoUsage autoUsage;
    private AutoType autoType;
    private SubscriptionType type;
    @ManyToOne
    private InsuranceCompany company;
    @ManyToOne
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

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public AutoType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(AutoType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public AutoUsage getVehicleUsage() {
        return vehicleUsage;
    }

    public void setVehicleUsage(AutoUsage vehicleUsage) {
        this.vehicleUsage = vehicleUsage;
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

    public SubscriptionType getType() {
        return type;
    }

    public void setType(SubscriptionType type) {
        this.type = type;
    }

    public InsuranceCompany getCompany() {
        return company;
    }

    public void setCompany(InsuranceCompany company) {
        this.company = company;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

}