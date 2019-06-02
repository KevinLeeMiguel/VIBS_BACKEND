package com.vibs_backend.vibs.serviceimpl;

import java.util.List;

import com.vibs_backend.vibs.dao.AutoTypeDao;
import com.vibs_backend.vibs.domain.AutoType;
import com.vibs_backend.vibs.service.IAutoTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutoTypeServiceimpl implements IAutoTypeService {

    @Autowired
    private AutoTypeDao dao;

    @Override
    public AutoType create(AutoType autoType) {
        return dao.save(autoType);
    }

    @Override
    public AutoType update(AutoType autoType) {
        return dao.save(autoType);
    }

    @Override
    public AutoType delete(AutoType autoType) {
        return dao.deleted(autoType);
    }

    @Override
    public AutoType findById(String autoTypeId) {
        return dao.getOne(autoTypeId);
    }

    @Override
    public List<AutoType> findAllByCompanyId(String id) {
        return dao.findByCompanyIdAndDeletedStatus(id,false);
    }

    @Override
    public List<AutoType> findAll() {
        return dao.findAll();
    }

    @Override
    public AutoType findByNameAndCompanyId(String name,String id) {
        return dao.findByNameAndCompanyIdAndDeletedStatus(name,id,false);
    }
    
}