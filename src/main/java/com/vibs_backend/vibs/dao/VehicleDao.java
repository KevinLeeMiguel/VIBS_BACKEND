package com.vibs_backend.vibs.dao;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.domain.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleDao extends JpaRepository<Vehicle,String>{
    public List<Vehicle>findByDeletedStatus(Boolean deletedStatus);
    public Optional<Vehicle>findByIdAndDeletedStatus(String id,Boolean deletedStatus);
    public List<Vehicle>findByOwnerReferenceIdAndDeletedStatus(String ownerId,Boolean deletedStatus);
}