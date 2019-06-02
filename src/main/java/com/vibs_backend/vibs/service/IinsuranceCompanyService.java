package com.vibs_backend.vibs.service;

import java.util.List;

import com.vibs_backend.vibs.domain.InsuranceCompany;



public interface IinsuranceCompanyService {
     InsuranceCompany create(InsuranceCompany ic);
     InsuranceCompany delete(InsuranceCompany ic);
     InsuranceCompany update(InsuranceCompany ic);
     InsuranceCompany findOne(String id);
     List<InsuranceCompany> findAll();
     List<InsuranceCompany> findAllFiltered();
}