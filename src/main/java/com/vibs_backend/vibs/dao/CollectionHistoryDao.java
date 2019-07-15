package com.vibs_backend.vibs.dao;

import com.vibs_backend.vibs.domain.CollectionHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionHistoryDao extends JpaRepository<CollectionHistory,String> {
    
}