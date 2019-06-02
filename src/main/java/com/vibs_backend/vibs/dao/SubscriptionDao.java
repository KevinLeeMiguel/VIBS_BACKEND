package com.vibs_backend.vibs.dao;

import java.util.List;

import com.vibs_backend.vibs.domain.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionDao extends JpaRepository<Subscription,String>{
    default Subscription deleted(Subscription entity) {
        entity.setDeletedStatus(true);
        return this.save(entity);
    }
    public List<Subscription>findByDeletedStatus(Boolean deletedStatus);

}