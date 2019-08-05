package com.vibs_backend.vibs.dao;

import java.util.Date;
import java.util.List;

import com.vibs_backend.vibs.domain.CollectionHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectionHistoryDao extends JpaRepository<CollectionHistory,String> {
    

    List<CollectionHistory>findByCollectionAccountId(String id);
    List<CollectionHistory>findByDoneAtBetween(Date start, Date end);
    Long countByDoneAtBetween(Date start,Date end);
    @Query("SELECT SUM(m.amount) FROM CollectionHistory m WHERE collection_account_id =?1 ")
    Double getTotalhistorybyic(String id);
    @Query("SELECT SUM(m.amount) FROM CollectionHistory m WHERE collection_account_id =?1 AND done_at BETWEEN ?2 AND ?3 ")
    public Double getTotalhistorybyicAnddates(String id, Date start,Date end);

}