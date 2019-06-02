package com.vibs_backend.vibs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibs_backend.vibs.domain.DeletedItem;
import com.vibs_backend.vibs.domain.Vehicle;
import com.vibs_backend.vibs.service.IDeletedItemService;
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
    @Autowired
    private IDeletedItemService diService;

    /**
     * Method to create a new  Vehicle
     * 
     * @param ic
     * @param request
     * @return
     */

    @PostMapping(value = "/save/customer/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody Vehicle ic,@PathVariable String id, HttpServletRequest request) {
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
     * method to get all  companies
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
    //  * Method to delete an  Vehicle
    //  * 
    //  * @param id
    //  * @return
    //  */
    // @DeleteMapping(value = "/delete/{id}")
    // public ResponseEntity<Object> deleteVehicle(@PathVariable String id) {
    //     ResponseBean rs = new ResponseBean();
    //     try {
    //         Vehicle ic = vehicleService.findById(id);
    //         if (ic != null) {
    //             DeletedItem di = new DeletedItem();
    //             byte[] data = ObjectToByteArray(ic);
    //             di.setItem(data);
    //             di.setName(ic.getClass().getName());
    //             diService.create(di);
    //             Vehicle icc = vehicleService.delete(ic);
    //             rs.setCode(200);
    //             rs.setDescription("success");
    //             rs.setObject(icc);
    //         } else {
    //             rs.setCode(404);
    //             rs.setDescription("Vehicle not found");
    //             rs.setObject(null);
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         rs.setCode(500);
    //         rs.setDescription("An Error occurred , please contact administrator");
    //         rs.setObject(null);
    //     }
    //     return new ResponseEntity<>(rs, HttpStatus.OK);
    // }

    // /**
    //  * Method to delete an  Vehicle
    //  * 
    //  * @param id
    //  * @return
    //  */
    // @DeleteMapping(value = "/recover/{id}")
    // public ResponseEntity<Object> recoverVehicle(@PathVariable String id) {
    //     ResponseBean rs = new ResponseBean();
    //     try {
    //         DeletedItem di = diService.findOne(id);
    //         if (di != null) {
    //             Object ob = byteArrayToObject(di.getItem());
    //             Vehicle ic = new Vehicle();
    //             if (ob != null) {
    //                 ObjectMapper obm = new ObjectMapper();
    //                 // JsonNode jsn = obm.reader(Object );
    //                 ic = (Vehicle) ob;
    //                 System.out.println("object is there");
    //             } else {
    //                 System.out.println("object is null");
    //             }
    //             rs.setCode(200);
    //             rs.setDescription("success");
    //             rs.setObject(ic);
    //         } else {
    //             rs.setCode(404);
    //             rs.setDescription("Vehicle not found");
    //             rs.setObject(null);
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         rs.setCode(500);
    //         rs.setDescription("An Error occurred , please contact administrator");
    //         rs.setObject(null);
    //     }
    //     return new ResponseEntity<>(rs, HttpStatus.OK);
    // }

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
            Vehicle ico = vehicleService.findById(id);
            if (ico != null) {
                ic.setDoneBy(username);
                ic.setId(ico.getId());
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

    // public static byte[] ObjectToByteArray(Object obj) {
    //     try {

    //         ByteArrayOutputStream bos = new ByteArrayOutputStream();
    //         ObjectOutputStream oos = new ObjectOutputStream(bos);
    //         oos.writeObject(obj);
    //         oos.flush();
    //         byte[] data = bos.toByteArray();
    //         return data;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // public static Object byteArrayToObject(byte[] data) throws IOException, ClassNotFoundException {
    //     Object obj = null;
    //     ByteArrayInputStream bis = null;
    //     ObjectInputStream ois = null;
    //     try {
    //         bis = new ByteArrayInputStream(data);
    //         ois = new ObjectInputStream(bis);
    //         obj = ois.readObject();
    //     } finally {
    //         if (bis != null) {
    //             bis.close();
    //         }
    //         if (ois != null) {
    //             ois.close();
    //         }
    //     }
    //     return obj;

    // }
}