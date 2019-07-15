package com.vibs_backend.vibs.dao;

import java.util.Optional;

import com.vibs_backend.vibs.domain.CollectionAccount;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionAccountDao extends JpaRepository<CollectionAccount,String> {
   Optional<CollectionAccount> findByCompanyId(String id);
}