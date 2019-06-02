package com.vibs_backend.vibs.service;

import java.util.List;

import com.vibs_backend.vibs.domain.AutoType;

public interface IAutoTypeService {

    public abstract AutoType create(AutoType autoType);
    public abstract AutoType update(AutoType autoType);
    public abstract AutoType delete(AutoType autoType);
    public abstract AutoType findById(String autoTypeId);
    public abstract List<AutoType> findAll();
    public abstract List<AutoType> findAllByCompanyId(String id);
    public abstract AutoType findByNameAndCompanyId(String name,String cid);
    
}