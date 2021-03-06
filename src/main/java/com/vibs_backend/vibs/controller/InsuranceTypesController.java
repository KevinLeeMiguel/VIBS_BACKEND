package com.vibs_backend.vibs.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.vibs_backend.vibs.dao.InsuranceTypeDao;
import com.vibs_backend.vibs.domain.InsuranceCompany;
import com.vibs_backend.vibs.domain.InsuranceType;
import com.vibs_backend.vibs.service.InsuranceCompanyService;
import com.vibs_backend.vibs.service.InsuranceTypesService;
import com.vibs_backend.vibs.utilities.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class InsuranceTypesController {
    @Autowired
    private InsuranceTypesService itypeService;
    @Autowired
    private InsuranceTypeDao itypeDao;
    @Autowired
    private InsuranceCompanyService icService;

    @PostMapping(value = "/general/insuranceTypes/save")
    public ResponseEntity<Object> createGeneral(@RequestBody InsuranceType iType,HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
                Optional<InsuranceType> itypen = itypeDao.findByNameAndIsGeneralAndDeletedStatus(iType.getName(),true,false);
                if(!itypen.isPresent()){
                    iType.setDoneBy(username);
                    iType.setIsGeneral(true);
                    itypeService.create(iType);
                    rs.setCode(200);
                    rs.setDescription("success");
                    rs.setObject(iType);
                }else{
                    rs.setCode(400);
                    rs.setDescription("Insurance Type with name: "+iType.getName()+", already exists");
                }
        } catch (Exception e) {
            rs.setCode(300);
            rs.setDescription("error occured");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    @PostMapping(value = "/insuranceCompanies/{id}/insuranceTypes/save")
    public ResponseEntity<Object> create(@RequestBody InsuranceType iType, @PathVariable String id,HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            Optional<InsuranceCompany> ic = icService.findOne(id);
            if(ic.isPresent()){
                Optional<InsuranceType> itypen = itypeService.findByNameAndCompany(iType.getName(), id);
                if(!itypen.isPresent()){
                    iType.setCompany(ic.get());
                    iType.setDoneBy(username);
                    itypeService.create(iType);
                    rs.setCode(200);
                    rs.setDescription("success");
                    rs.setObject(iType);
                }else{
                    rs.setCode(400);
                    rs.setDescription("Insurance Type with name: "+iType.getName()+", already exists");
                }
            }else{
                rs.setCode(404);
                rs.setDescription("company not found");
            }
        } catch (Exception e) {
            rs.setCode(300);
            rs.setDescription("error occured");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    @GetMapping(value="/insuranceCompanies/{id}/insuranceTypes")
    public ResponseEntity<Object> findAllByCompany(@PathVariable String id){
        ResponseBean rs = new ResponseBean();
        try {
            Optional<InsuranceCompany> ic = icService.findOne(id);
            if(ic.isPresent()){
                List<InsuranceType> li = itypeService.findByCompany(ic.get().getId());
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(li);
            }else{
                rs.setCode(404);
                rs.setDescription("company doesn't exist");
            }
        } catch (Exception e) {
            rs.setCode(300);
            rs.setDescription("Error occured");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    @GetMapping(value="/general/insuranceTypes")
    public ResponseEntity<Object> findAllGeneral(){
        ResponseBean rs = new ResponseBean();
        try {
                List<InsuranceType> li = itypeDao.findByIsGeneralAndDeletedStatus(true, false);
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(li);
        } catch (Exception e) {
            rs.setCode(300);
            rs.setDescription("Error occured");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }
    
}