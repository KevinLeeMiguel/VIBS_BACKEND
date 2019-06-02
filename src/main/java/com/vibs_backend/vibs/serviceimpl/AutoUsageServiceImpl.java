package com.vibs_backend.vibs.serviceimpl;

import java.util.List;

import com.vibs_backend.vibs.dao.AutoUsageDao;
import com.vibs_backend.vibs.domain.AutoUsage;
import com.vibs_backend.vibs.service.IAutoUsageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoUsageServiceImpl implements IAutoUsageService {
    @Autowired
    private AutoUsageDao dao;

    @Override
    public AutoUsage create(AutoUsage autoUsage) {
        return dao.save(autoUsage);
    }

    @Override
    public AutoUsage update(AutoUsage autoUsage) {
        return dao.save(autoUsage);
    }

    @Override
    public AutoUsage delete(AutoUsage autoUsage) {
        return dao.deleted(autoUsage);
    }

    @Override
    public AutoUsage findById(String autoUsageId) {
        return dao.getOne(autoUsageId);
    }

    @Override
    public List<AutoUsage> findAll() {
        return dao.findAll();
    }

    @Override
    public List<AutoUsage> findAllByCompanyId(String id) {
        return dao.findByCompanyIdAndDeletedStatus(id,false);
    }

    @Override
    public AutoUsage findByNameAndCompanyId(String name, String cid) {
        return dao.findByNameAndCompanyIdAndDeletedStatus(name, cid,false);
    }
    
}