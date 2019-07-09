package com.vibs_backend.vibs.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.vibs_backend.vibs.domain.AutoType;
import com.vibs_backend.vibs.domain.AutoUsage;
import com.vibs_backend.vibs.domain.InsuranceCompany;
import com.vibs_backend.vibs.domain.InsuranceType;
import com.vibs_backend.vibs.domain.Subscription;
import com.vibs_backend.vibs.domain.SubscriptionStatus;
import com.vibs_backend.vibs.domain.Vehicle;
import com.vibs_backend.vibs.service.IAutoTypeService;
import com.vibs_backend.vibs.service.IAutoUsageService;
import com.vibs_backend.vibs.service.ISubscriptionService;
import com.vibs_backend.vibs.service.IVehicleService;
import com.vibs_backend.vibs.service.IinsuranceCompanyService;
import com.vibs_backend.vibs.service.IinsuranceTypesService;
import com.vibs_backend.vibs.utilities.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private ISubscriptionService sService;
    @Autowired
    private IVehicleService vService;
    @Autowired
    private IinsuranceCompanyService icService;
    @Autowired
    private IAutoTypeService atypeService;
    @Autowired
    private IAutoUsageService aUsageService;
    @Autowired
    private IinsuranceTypesService itypeService;

    @PostMapping(value = "/save")
    public ResponseEntity<Object> create(@RequestBody SubscriptionReq sq, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            Optional<Vehicle> v = vService.findById(sq.getVehicleId());
            if (v.isPresent()) {
                Optional<InsuranceCompany> ic = icService.findOne(sq.getCompanyId());
                if (ic.isPresent()) {
                    AutoType atype = atypeService.findById(sq.getAutoTypeId());
                    if (atype != null) {
                        AutoUsage aUsage = aUsageService.findById(sq.getAutoUsageId());
                        if (aUsage != null) {
                            Optional<InsuranceType> itype = itypeService.findById(sq.getTypeId());
                            if (itype.isPresent()) {
                                Subscription s = sq.getSub();
                                s.setId(UUID.randomUUID().toString());
                                s.setDoneBy(username);
                                s.setVehicle(v.get());
                                s.setCompanyReferenceId(ic.get().getId());
                                s.setAutoType(atype);
                                s.setAutoUsage(aUsage);
                                s.setType(itype.get());
                                s.setStatus(SubscriptionStatus.PENDING);
                                sService.create(s);
                                rs.setCode(200);
                                rs.setDescription("success");
                                // rs.setObject(s);
                            } else {
                                rs.setCode(404);
                                rs.setDescription("invalid insurance type");
                            }
                        } else {
                            rs.setCode(404);
                            rs.setDescription("invalid Vehicle Usage");
                        }
                    } else {
                        rs.setCode(404);
                        rs.setDescription("invalid Vehicle Type");
                    }
                } else {
                    rs.setCode(404);
                    rs.setDescription("Insurance company not found");
                }
            } else {
                rs.setCode(404);
                rs.setDescription("Vehicle not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping(value = "/company/{id}/all")
    public ResponseEntity<Object> getAll(@PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            List<Subscription> li = sService.findAllByCompany(id);
            rs.setCode(200);
            rs.setDescription("success");
            rs.setObject(li);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getOneById(@PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<Subscription> li = sService.findById(id);
            if (li.isPresent()) {
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(li.get());
            } else {
                rs.setCode(404);
                rs.setDescription("Not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping(value = "/company/{id}/status/{status}")
    public ResponseEntity<Object> getAllByCompanyAndStatus(@PathVariable String id, @PathVariable String status,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            SubscriptionStatus st = sService.getStatus(status);
            if(st != null){
                List<Subscription> li = sService.findAllByCompanyAndStatus(id, st);
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(li);
            }else{
                rs.setCode(404);
                rs.setDescription("Invalid status");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @GetMapping(value = "/customer/{username}/status/{status}")
    public ResponseEntity<Object> getAllByCustomerAndStatus(@PathVariable String username, @PathVariable String status,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            SubscriptionStatus st = sService.getStatus(status);
            if(st != null){
                List<Subscription> li = sService.findAllByUsernameAndStatus(username, st);
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(li);
            }else{
                rs.setCode(404);
                rs.setDescription("Invalid status");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
    public static class SubscriptionReq {
        private Subscription sub;
        private String autoTypeId;
        private String autoUsageId;
        private String typeId;
        private String vehicleId;
        private String companyId;

        public Subscription getSub() {
            return sub;
        }

        public void setSub(Subscription sub) {
            this.sub = sub;
        }

        public String getAutoTypeId() {
            return autoTypeId;
        }

        public void setAutoTypeId(String autoTypeId) {
            this.autoTypeId = autoTypeId;
        }

        public String getAutoUsageId() {
            return autoUsageId;
        }

        public void setAutoUsageId(String autoUsageId) {
            this.autoUsageId = autoUsageId;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

    }

}