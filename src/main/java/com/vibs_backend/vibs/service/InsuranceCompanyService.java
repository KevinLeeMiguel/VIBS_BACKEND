package com.vibs_backend.vibs.service;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.domain.InsuranceCompany;



public interface InsuranceCompanyService {
     InsuranceCompany create(InsuranceCompany ic);
     InsuranceCompany delete(InsuranceCompany ic);
     InsuranceCompany update(InsuranceCompany ic);
     Optional<InsuranceCompany> findOne(String id);
     List<InsuranceCompany> findAll();
     List<InsuranceCompany> findAllFiltered();
}