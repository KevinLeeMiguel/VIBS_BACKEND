package com.vibs_backend.vibs.serviceimpl;

import java.util.List;

import com.vibs_backend.vibs.dao.InsuranceCompanyDao;
import com.vibs_backend.vibs.domain.InsuranceCompany;
import com.vibs_backend.vibs.service.IinsuranceCompanyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InsuranceCompanyServiceImpl implements IinsuranceCompanyService {

    @Autowired
    private InsuranceCompanyDao dao;

    @Override
    public InsuranceCompany create(InsuranceCompany ic) {
        return dao.save(ic);
    }

    @Override
    public InsuranceCompany delete(InsuranceCompany ic) {
        ic.setDeletedStatus(true);
       return dao.save(ic);
    }

    @Override
    public InsuranceCompany update(InsuranceCompany ic) {
        return dao.save(ic);
    }

    @Override
    public InsuranceCompany findOne(String id) {
        return dao.getOne(id);
    }

    @Override
    public List<InsuranceCompany> findAll() {
        return dao.findAll();
    }

    @Override
    public List<InsuranceCompany> findAllFiltered() {
        return dao.findByDeletedStatus(false);
    }
}