package com.vibs_backend.vibs.service;

import java.util.List;

import com.vibs_backend.vibs.domain.AutoUsage;

public interface IAutoUsageService {

    public abstract AutoUsage create(AutoUsage autoUsage);
    public abstract AutoUsage update(AutoUsage autoUsage);
    public abstract AutoUsage delete(AutoUsage autoUsage);
    public abstract AutoUsage findById(String autoUsageId);
    public abstract List<AutoUsage> findAll();
    public abstract List<AutoUsage> findAllByCompanyId(String id);
    public abstract AutoUsage findByNameAndCompanyId(String name,String cid);
}