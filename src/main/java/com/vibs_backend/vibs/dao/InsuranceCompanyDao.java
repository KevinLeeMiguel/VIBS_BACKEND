package com.vibs_backend.vibs.dao;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.domain.InsuranceCompany;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCompanyDao extends JpaRepository<InsuranceCompany, String> {
    default InsuranceCompany deleted(InsuranceCompany entity) {
        entity.setDeletedStatus(true);
        return this.save(entity);
    }
    public List<InsuranceCompany>findByDeletedStatus(Boolean deletedStatus);
     Optional<InsuranceCompany> findByName(String name);
    Long countAllByDeletedStatus(boolean status);

}