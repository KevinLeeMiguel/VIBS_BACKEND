package com.vibs_backend.vibs.service;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.domain.InsuranceType;

public interface InsuranceTypesService {
    public abstract InsuranceType create(InsuranceType it);
    public abstract InsuranceType update(InsuranceType it);
    public abstract InsuranceType delete(InsuranceType it);
    public abstract List<InsuranceType> findAll();
    public abstract List<InsuranceType> findByCompany(String companyId);
    public abstract Optional<InsuranceType> findByNameAndCompany(String name,String companyId);
    public abstract Optional<InsuranceType> findById(String typeId);
}