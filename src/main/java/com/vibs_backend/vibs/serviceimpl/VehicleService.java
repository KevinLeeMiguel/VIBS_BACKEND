package com.vibs_backend.vibs.serviceimpl;

import java.util.List;

import com.vibs_backend.vibs.dao.VehicleDao;
import com.vibs_backend.vibs.domain.Vehicle;
import com.vibs_backend.vibs.service.IVehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService implements IVehicleService {

    @Autowired
    private VehicleDao dao;

    @Override
    public Vehicle create(Vehicle vehicle) {
        return dao.save(vehicle);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        return dao.save(vehicle);
    }

    @Override
    public Vehicle delete(Vehicle vehicle) {
        vehicle.setDeletedStatus(true);
        return dao.save(vehicle);
    }

    @Override
    public Vehicle findById(String vehicleId) {
        return dao.getOne(vehicleId);
    }

    @Override
    public List<Vehicle> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Vehicle> findAllByOwnerId(String id) {
        return dao.findByOwnerReferenceIdAndDeletedStatus(id, false);
    }

}