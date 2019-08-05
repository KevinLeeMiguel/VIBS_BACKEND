package com.vibs_backend.vibs.dao;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.domain.InsuranceType;

import org.springframework.data.jpa.repository.JpaRepository;


public interface InsuranceTypeDao extends JpaRepository<InsuranceType,String> {
    default InsuranceType deleted(InsuranceType entity) {
        entity.setDeletedStatus(true);
        return this.save(entity);
    }
    List<InsuranceType> findByCompanyIdAndDeletedStatus(String id,Boolean dStatus);
    List<InsuranceType> findByDeletedStatus(Boolean st);
    List<InsuranceType> findByIsGeneralAndDeletedStatus(Boolean isG,boolean st);
    Optional<InsuranceType> findByIdAndDeletedStatus(String id,Boolean st);
    public Optional<InsuranceType> findByNameAndCompanyIdAndDeletedStatus(String name,String id,boolean deletedStatus);
    public Optional<InsuranceType> findByNameAndIsGeneralAndDeletedStatus(String name,boolean isG,boolean deletedStatus);
}