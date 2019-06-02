package com.vibs_backend.vibs.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity
@EntityListeners(AuditingEntityListener.class)
public class InsuranceCompany implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    private String id = UUID.randomUUID().toString();
    private  String name;
    private String description;
    private long tin;
    private String phone;
    private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<AutoType> autoTypes;
    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<AutoUsage> autoUsages;
    @Column(nullable = false, updatable = false)
	private Date doneAt = new Date();
	@JsonIgnore
	private String doneBy = "";
    @JsonIgnore
	private Date lastUpdatedAt; // = null;
	@JsonIgnore
	private String lastUpdatedBy = "";
    private Boolean deletedStatus = false;

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    public Boolean getDeletedStatus() {
        return deletedStatus;
    }

    public void setDeletedStatus(Boolean deletedStatus) {
        this.deletedStatus = deletedStatus;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return long return the tin
     */
    public long getTin() {
        return tin;
    }

    /**
     * @param tin the tin to set
     */
    public void setTin(long tin) {
        this.tin = tin;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return the doneAt
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
     * @return the doneBy
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
     * @return the lastUpdatedAt
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
     * @return the lastUpdatedBy
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

        public List<AutoType> getAutoTypes() {
            return autoTypes;
        }

        public void setAutoTypes(List<AutoType> autoTypes) {
            this.autoTypes = autoTypes;
        }

        public List<AutoUsage> getAutoUsages() {
            return autoUsages;
        }

        public void setAutoUsages(List<AutoUsage> autoUsages) {
            this.autoUsages = autoUsages;
        }

    @Override
    public String toString() {
        return "InsuranceCompany [ deletedStatus="
                + deletedStatus + ", description=" + description + ", doneAt=" + doneAt + ", doneBy=" + doneBy
                + ", email=" + email + ", id=" + id + ", lastUpdatedAt=" + lastUpdatedAt + ", lastUpdatedBy="
                + lastUpdatedBy + ", name=" + name + ", phone=" + phone + ", tin=" + tin + "]";
    }

}