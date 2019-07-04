package com.vibs_backend.vibs.serviceimpl;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.dao.InsuranceTypeDao;
import com.vibs_backend.vibs.domain.InsuranceType;
import com.vibs_backend.vibs.service.IInsuranceTypesService;

import org.springframework.beans.factory.annotation.Autowired;

public class InsuranceTypeServiceImpl implements IInsuranceTypesService {

    @Autowired
    private InsuranceTypeDao dao;

    @Override
    public InsuranceType create(InsuranceType it) {
        return dao.save(it);
    }

    @Override
    public InsuranceType update(InsuranceType it) {
        return dao.save(it);
    }

    @Override
    public InsuranceType delete(InsuranceType it) {
        return dao.deleted(it);
    }

    @Override
    public List<InsuranceType> findAll() {
        return dao.findByDeletedStatus(false);
    }

    @Override
    public List<InsuranceType> findByCompany(String companyId) {
        return dao.findByCompanyIdAndDeletedStatus(companyId, false);
    }

    @Override
    public Optional<InsuranceType> findById(String typeId) {
        return null;
    }

}