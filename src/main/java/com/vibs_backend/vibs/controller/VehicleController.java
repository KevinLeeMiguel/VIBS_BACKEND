package com.vibs_backend.vibs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.vibs_backend.vibs.domain.Vehicle;
import com.vibs_backend.vibs.service.IVehicleService;
import com.vibs_backend.vibs.utilities.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private IVehicleService vehicleService;

    /**
     * Method to create a new Vehicle
     * 
     * @param ic
     * @param request
     * @return
     */

    @PostMapping(value = "/save/customer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody Vehicle ic, @PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            ic.setDoneBy(username);
            ic.setLastUpdatedAt(new Date());
            ic.setLastUpdatedBy(username);
            ic.setOwnerReferenceId(id);
            Vehicle icd = vehicleService.create(ic);
            rs.setCode(200);
            rs.setDescription("Saved successfully");
            rs.setObject(icd);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occurred while saving the  Vehicle");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    /**
     * method to get all companies
     * 
     * @return
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> getVehicles() {
        ResponseBean rs = new ResponseBean();
        try {
            List<Vehicle> li = vehicleService.findAll();
            rs.setCode(200);
            rs.setDescription("");
            rs.setObject(li);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occurred while fetching  Companies");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping(value = "/all/customer/{id}")
    public ResponseEntity<Object> getVehiclesByCustomerId(@PathVariable String id) {
        ResponseBean rs = new ResponseBean();
        try {
            List<Vehicle> li = vehicleService.findAllByOwnerId(id);
            rs.setCode(200);
            rs.setDescription("");
            rs.setObject(li);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occurred while fetching  Companies");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    // /**
    // * Method to delete an Vehicle
    // *
    // * @param id
    // * @return
    // */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteVehicle(@PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        String username = request.getHeader("doneBy");
        try {
            Optional<Vehicle> ic = vehicleService.findById(id);
            if (ic.isPresent()) {
                ic.get().setLastUpdatedAt(new Date());
                ic.get().setLastUpdatedBy(username);
                vehicleService.delete(ic.get());
                rs.setCode(200);
                rs.setDescription("success");
            } else {
                rs.setCode(404);
                rs.setDescription("Vehicle not found");
                rs.setObject(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("An Error occurred , please contact administrator");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    /**
     * method to update
     * 
     * @param ic
     * @param request
     * @return
     */
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateVehicle(@PathVariable String id, @RequestBody Vehicle ic,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            Optional<Vehicle> ico = vehicleService.findById(id);
            if (ico.isPresent()) {
                ic.setDoneBy(username);
                ic.setId(ico.get().getId());
                ic.setLastUpdatedAt(new Date());
                ic.setLastUpdatedBy(username);
                vehicleService.update(ic);
                rs.setCode(200);
                rs.setDescription("Updated successfully");
                rs.setObject(ic);
            } else {
                rs.setCode(404);
                rs.setDescription("The Vehicle you are trying to update was not found");
                rs.setObject(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occurred while saving the  Vehicle");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<Vehicle> v = vehicleService.findById(id);
            if (v.isPresent()) {
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("Error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

}