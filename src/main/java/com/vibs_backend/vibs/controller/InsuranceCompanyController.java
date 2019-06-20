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
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vibs_backend.vibs.domain.DeletedItem;
import com.vibs_backend.vibs.domain.InsuranceCompany;
import com.vibs_backend.vibs.service.IDeletedItemService;
import com.vibs_backend.vibs.service.IinsuranceCompanyService;
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
@RequestMapping("/insurancecompanies")
public class InsuranceCompanyController {
    @Autowired
    private IinsuranceCompanyService icService;
    @Autowired
    private IDeletedItemService diService;

    /**
     * Method to create a new Insurance Company
     * 
     * @param ic
     * @param request
     * @return
     */

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody InsuranceCompany ic, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            ic.setDoneBy(username);
            ic.setLastUpdatedAt(new Date());
            ic.setLastUpdatedBy(username);
            InsuranceCompany icd = icService.create(ic);
            rs.setCode(200);
            rs.setDescription("Saved successfully");
            rs.setObject(icd);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occured while saving the Insurance Company");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    /**
     * method to get all insurance companies
     * 
     * @return
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> getCompanies() {
        ResponseBean rs = new ResponseBean();
        try {
            List<InsuranceCompany> li = icService.findAllFiltered();
            rs.setCode(200);
            rs.setDescription("");
            rs.setObject(li);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occured while fetching Insurance Companies");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

        /**
     * method to get insurance company by id
     * 
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getCompany(@PathVariable String id) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<InsuranceCompany> ic = icService.findOne(id);
            if(ic.isPresent()){
                rs.setCode(200);
                rs.setDescription("");
                rs.setObject(ic.get());
            }else{
                rs.setCode(404);
                rs.setDescription("Insurance company not found");
                rs.setObject(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occured while fetching Insurance Companies");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
    /**
     * Method to delete an insurance company
     * 
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable String id) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<InsuranceCompany> ic = icService.findOne(id);
            if (ic.isPresent()) {
                DeletedItem di = new DeletedItem();
                byte[] data = ObjectToByteArray(ic);
                di.setItem(data);
                di.setName(ic.getClass().getName());
                diService.create(di);
                InsuranceCompany icc = icService.delete(ic.get());
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(icc);
            } else {
                rs.setCode(404);
                rs.setDescription("Company not found");
                rs.setObject(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("An Error occured , please contact administrator");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    /**
     * Method to delete an insurance company
     * 
     * @param id
     * @return
     */
    @DeleteMapping(value = "/recover/{id}")
    public ResponseEntity<Object> recoverCompany(@PathVariable String id) {
        ResponseBean rs = new ResponseBean();
        try {
            DeletedItem di = diService.findOne(id);
            if (di != null) {
                Object ob = byteArrayToObject(di.getItem());
                InsuranceCompany ic = new InsuranceCompany();
                if (ob != null) {
                    ObjectMapper obm = new ObjectMapper();
                    // JsonNode jsn = obm.reader(Object );
                    ic = (InsuranceCompany) ob;
                    System.out.println("object is there");
                } else {
                    System.out.println("object is null");
                }
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(ic);
            } else {
                rs.setCode(404);
                rs.setDescription("Company not found");
                rs.setObject(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("An Error occured , please contact administrator");
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
    public ResponseEntity<Object> updateCompany(@PathVariable String id, @RequestBody InsuranceCompany ic,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            Optional<InsuranceCompany> ico = icService.findOne(id);
            if (ico.isPresent()) {
                ic.setDoneBy(username);
                ic.setId(ico.get().getId());
                ic.setLastUpdatedAt(new Date());
                ic.setLastUpdatedBy(username);
                InsuranceCompany icd = icService.update(ic);
                rs.setCode(200);
                rs.setDescription("Updated successfully");
                rs.setObject(icd);
            } else {
                rs.setCode(404);
                rs.setDescription("The Company you are trying to update was not found");
                rs.setObject(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occured while saving the Insurance Company");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);

    }

    public static byte[] ObjectToByteArray(Object obj) {
        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            byte[] data = bos.toByteArray();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object byteArrayToObject(byte[] data) throws IOException, ClassNotFoundException {
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return obj;

    }
}