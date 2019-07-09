package com.vibs_backend.vibs.service;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.domain.Vehicle;

public interface IVehicleService {
    public abstract Vehicle create(Vehicle vehicle);
    public abstract Vehicle update(Vehicle vehicle);
    public abstract Vehicle delete(Vehicle vehicle);
    public abstract Optional<Vehicle> findById(String vehicleId);
    public abstract List<Vehicle> findAll();
    public abstract List<Vehicle> findAllByOwnerId(String id);
}