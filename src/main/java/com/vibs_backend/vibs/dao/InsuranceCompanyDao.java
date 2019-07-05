package com.vibs_backend.vibs.dao;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.domain.InsuranceCompany;
import com.vibs_backend.vibs.domain.InsuranceType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCompanyDao extends JpaRepository<InsuranceCompany, String> {
    
    public List<InsuranceCompany>findByDeletedStatus(Boolean deletedStatus);

}