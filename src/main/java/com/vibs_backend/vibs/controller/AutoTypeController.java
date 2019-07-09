package com.vibs_backend.vibs.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.vibs_backend.vibs.domain.AutoType;
import com.vibs_backend.vibs.domain.InsuranceCompany;
import com.vibs_backend.vibs.service.IAutoTypeService;
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
public class AutoTypeController {

    @Autowired
    private IAutoTypeService atService;
    @Autowired
    private IinsuranceCompanyService icService;

    @PostMapping(value = "/insurancecompanies/{id}/autotypes/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@PathVariable String id, @RequestBody AutoType at,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<InsuranceCompany> ic = icService.findOne(id);
            AutoType atn = atService.findByNameAndCompanyId(at.getName(),id);
            if(ic.isPresent()){
                if(atn == null){

                    String username = request.getHeader("doneBy");
                    at.setCompany(ic.get());
                    at.setDoneBy(username);
                    at.setLastUpdatedAt(new Date());
                    at.setLastUpdatedBy(username);
                    AutoType atd = atService.create(at);
                    rs.setCode(200);
                    rs.setDescription("Saved successfully");
                    rs.setObject(atd);
                }else{
                    rs.setCode(400);
                    rs.setDescription("AutoType with name: "+at.getName()+" already exists");
                }
            }else{
                rs.setCode(404);
                rs.setDescription("The Insurance Company Specified wasn't found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error Occured contact administrator");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    @GetMapping(value="/insurancecompanies/{id}/autotypes")
    public ResponseEntity<Object> getAutoTypesByCompany(@PathVariable String id,HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<InsuranceCompany> ic = icService.findOne(id);
            if(ic.isPresent()){
                List<AutoType> types = atService.findAllByCompanyId(id);
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(types);
            }else{
                rs.setCode(404);
                rs.setDescription("Company not found");
            }
        } catch (Exception e) {
            rs.setCode(500);
            rs.setDescription("Error Occured please contact administrator");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }
    
    @PutMapping(value="/autotypes/{id}/update",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody AutoType autoType,HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            AutoType at = atService.findById(id);
            if (at != null) {
                autoType.setDoneBy(username);
                autoType.setId(at.getId());
                autoType.setCompany(at.getCompany());
                autoType.setLastUpdatedAt(new Date());
                autoType.setLastUpdatedBy(username);
                atService.update(autoType);
                rs.setCode(200);
                rs.setDescription("Updated successfully");
                rs.setObject(autoType);
            }else{
                rs.setCode(404);
                rs.setDescription("The AutoType you are trying to update was not found");
                rs.setObject(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occured, Contact Administrator");
            rs.setObject(null);
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @DeleteMapping(value="/autotypes/{id}/delete",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable String id,HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            AutoType at = atService.findById(id);
            if (at != null) {
                at.setLastUpdatedAt(new Date());
                at.setLastUpdatedBy(username);
                atService.delete(at);
                rs.setCode(200);
                rs.setDescription("deleted successfully");
            }else{
                rs.setCode(404);
                rs.setDescription("The AutoType you are trying to update was not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occured, Contact Administrator");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
}