package com.vibs_backend.vibs.dao;

import java.util.Date;
import java.util.List;

import com.vibs_backend.vibs.domain.CollectionHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionHistoryDao extends JpaRepository<CollectionHistory,String> {
    

    List<CollectionHistory>findByCollectionAccountId(String id);
    List<CollectionHistory>findByDoneAtBetween(Date start, Date end);
    Long countByDoneAtBetween(Date start,Date end);

}