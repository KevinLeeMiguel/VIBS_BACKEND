package com.vibs_backend.vibs.dao;


import java.util.List;

import com.vibs_backend.vibs.domain.AutoUsage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoUsageDao extends JpaRepository<AutoUsage,String>{
    
    default AutoUsage deleted(AutoUsage entity) {
        entity.setDeletedStatus(true);
        return this.save(entity);
    }

    public AutoUsage findByNameAndCompanyIdAndDeletedStatus(String name,String id,boolean deletedStatus);
    public List<AutoUsage> findByCompanyIdAndDeletedStatus(String id,boolean deletedStatus);
}