package com.vibs_backend.vibs.dao;

import com.vibs_backend.vibs.domain.VehicleImage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleImageDao extends JpaRepository<VehicleImage,String> {
    
}