package com.vibs_backend.vibs.dao;

import com.vibs_backend.vibs.domain.SubscriptionContract;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionContractDao extends JpaRepository<SubscriptionContract,String>{
    
}