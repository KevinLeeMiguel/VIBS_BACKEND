package com.vibs_backend.vibs.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.vibs_backend.vibs.dao.AutoUsageDao;
import com.vibs_backend.vibs.domain.AutoUsage;
import com.vibs_backend.vibs.domain.InsuranceCompany;
import com.vibs_backend.vibs.service.IAutoUsageService;
import com.vibs_backend.vibs.service.IinsuranceCompanyService;
import com.vibs_backend.vibs.utilities.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutoUsageController {
    @Autowired
    private IAutoUsageService auService;
    @Autowired
    private AutoUsageDao auDao;
    @Autowired
    private IinsuranceCompanyService icService;

    @PostMapping(value = "/general/autousages/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createGeneral(@RequestBody AutoUsage au, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            AutoUsage atn = auDao.findByNameAndIsGeneralAndDeletedStatus(au.getName(),true,false);
                if (atn == null) {
                    String username = request.getHeader("doneBy");
                    au.setDoneBy(username);
                    au.setLastUpdatedAt(new Date());
                    au.setIsGeneral(true);
                    au.setLastUpdatedBy(username);
                    AutoUsage atd = auService.create(au);
                    rs.setCode(200);
                    rs.setDescription("Saved successfully");
                    rs.setObject(atd);
                } else {
                    rs.setCode(400);
                    rs.setDescription("AutoUsage with name: " + au.getName() + " already exists");
                }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error Occured contact administrator");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @PostMapping(value = "/insurancecompanies/{id}/autousages/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@PathVariable String id, @RequestBody AutoUsage au,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<InsuranceCompany> ic = icService.findOne(id);
            AutoUsage atn = auService.findByNameAndCompanyId(au.getName(), id);
            if (ic.isPresent()) {
                if (atn == null) {
                    String username = request.getHeader("doneBy");
                    au.setCompany(ic.get());
                    au.setDoneBy(username);
                    au.setLastUpdatedAt(new Date());
                    au.setLastUpdatedBy(username);
                    AutoUsage atd = auService.create(au);
                    rs.setCode(200);
                    rs.setDescription("Saved successfully");
                    rs.setObject(atd);
                } else {
                    rs.setCode(400);
                    rs.setDescription("AutoUsage with name: " + au.getName() + " already exists");
                }
            } else {
                rs.setCode(404);
                rs.setDescription("The Insurance Company Specified wasn't found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error Occured contact administrator");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping(value = "/insurancecompanies/{id}/autousages")
    public ResponseEntity<Object> getAutoUsagesByCompany(@PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<InsuranceCompany> ic = icService.findOne(id);
            if (ic.isPresent()) {
                List<AutoUsage> types = auService.findAllByCompanyId(id);
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(types);
            } else {
                rs.setCode(404);
                rs.setDescription("Company not found");
            }
        } catch (Exception e) {
            rs.setCode(500);
            rs.setDescription("Error Occured please contact administrator");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @PutMapping(value = "/autousages/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody AutoUsage autoUsage,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            AutoUsage au = auService.findById(id);
            if (au != null) {
                autoUsage.setDoneBy(username);
                autoUsage.setId(au.getId());
                autoUsage.setCompany(au.getCompany());
                autoUsage.setLastUpdatedAt(new Date());
                autoUsage.setLastUpdatedBy(username);
                auService.update(autoUsage);
                rs.setCode(200);
                rs.setDescription("Updated successfully");
                rs.setObject(autoUsage);
            } else {
                rs.setCode(404);
                rs.setDescription("The AutoUsage you are trying to update was not found");
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

    @DeleteMapping(value = "/autousages/{id}/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> delete(@PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            AutoUsage au = auService.findById(id);
            if (au != null) {
                au.setLastUpdatedAt(new Date());
                au.setLastUpdatedBy(username);
                auService.delete(au);
                rs.setCode(200);
                rs.setDescription("deleted successfully");
            } else {
                rs.setCode(404);
                rs.setDescription("The AutoUsage you are trying to update was not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(500);
            rs.setDescription("Error occured, Contact Administrator");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping(value = "/general/autousages")
    public ResponseEntity<Object> getGeneralAutoUsages(HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
                List<AutoUsage> types = auDao.findByIsGeneralAndDeletedStatus(true, false);
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(types);
        } catch (Exception e) {
            rs.setCode(500);
            rs.setDescription("Error Occured please contact administrator");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
}