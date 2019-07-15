package com.vibs_backend.vibs.dao;

import com.vibs_backend.vibs.domain.PaymentTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionDao extends JpaRepository<PaymentTransaction,String> {
    
}