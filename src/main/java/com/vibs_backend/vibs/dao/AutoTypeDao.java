package com.vibs_backend.vibs.dao;

import java.util.List;

import com.vibs_backend.vibs.domain.AutoType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoTypeDao extends JpaRepository<AutoType, String> {
    
   default AutoType deleted(AutoType entity) {
        entity.setDeletedStatus(true);
        return this.save(entity);
    }
    public AutoType findByNameAndCompanyIdAndDeletedStatus(String name,String id,boolean deletedStatus);
    public AutoType findByNameAndIsGeneralAndDeletedStatus(String name,Boolean isG,boolean deletedStatus);
    public List<AutoType> findByCompanyIdAndDeletedStatus(String id,boolean deletedStatus);
    public List<AutoType> findByIsGeneralAndDeletedStatus(boolean isG,boolean deletedStatus);


}