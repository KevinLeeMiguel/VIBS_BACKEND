package com.vibs_backend.vibs.dao;

import com.vibs_backend.vibs.domain.DeletedItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedItemDao extends JpaRepository<DeletedItem,String> {
    
}