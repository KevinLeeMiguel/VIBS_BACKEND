package com.vibs_backend.vibs.controller;

import java.util.HashMap;
import java.util.Map;

import com.vibs_backend.vibs.dao.InsuranceCompanyDao;
import com.vibs_backend.vibs.dao.SubscriptionDao;
import com.vibs_backend.vibs.domain.SubscriptionStatus;
import com.vibs_backend.vibs.utilities.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    private InsuranceCompanyDao icService;
    @Autowired
    private SubscriptionDao sService;

    @GetMapping(value="/insurancecompanies/count")
    public ResponseEntity<Object> countIcs() {
        ResponseBean rs = new ResponseBean();
        try {
            Long count = icService.countAllByDeletedStatus(false);
            rs.setCode(200);
            rs.setObject(count);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("Error Occurred");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    @GetMapping(value="/collectionhistory/countbydate")
    public ResponseEntity<Object> countchs(@RequestBody Map<String,String> map) {
        ResponseBean rs = new ResponseBean();
        try {
            Long count = icService.countAllByDeletedStatus(false);
            rs.setCode(200);
            rs.setObject(count);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("Error Occurred");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    @GetMapping(value="/subscriptions/count/{companyId}")
    public ResponseEntity<Object> countSs(@PathVariable String companyId) {
        ResponseBean rs = new ResponseBean();
        try {
            Long pending = sService.countAllByCompanyReferenceIdAndStatusAndDeletedStatus(companyId,SubscriptionStatus.PENDING, false);
            Long approve = sService.countAllByCompanyReferenceIdAndStatusAndDeletedStatus(companyId,SubscriptionStatus.APPROVED, false);
            Long rejected = sService.countAllByCompanyReferenceIdAndStatusAndDeletedStatus(companyId,SubscriptionStatus.REJECTED, false);
            Long paid = sService.countAllByCompanyReferenceIdAndStatusAndPaymentStatusAndDeletedStatus(companyId,SubscriptionStatus.APPROVED,true, false);
            Long all = sService.countAllByCompanyReferenceIdAndDeletedStatus(companyId, false);
            HashMap<String,Object> map  = new HashMap<>();
            map.put("pending", pending);
            map.put("approved", approve);
            map.put("rejected", rejected);
            map.put("paid", paid);
            map.put("all", all);
            rs.setCode(200);
            rs.setObject(map);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("error Occured");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }
    
}