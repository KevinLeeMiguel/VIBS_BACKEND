package com.vibs_backend.vibs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.vibs_backend.vibs.dao.CollectionAccountDao;
import com.vibs_backend.vibs.dao.CollectionHistoryDao;
import com.vibs_backend.vibs.dao.InsuranceCompanyDao;
import com.vibs_backend.vibs.dao.SubscriptionDao;
import com.vibs_backend.vibs.domain.CollectionAccount;
import com.vibs_backend.vibs.domain.InsuranceCompany;
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
    @Autowired
    private CollectionHistoryDao chService;
    @Autowired
    private CollectionAccountDao caService;

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

    @GetMapping(value="/collectionhistory/countAll/ic/{id}")
    public ResponseEntity<Object> totalchs(@PathVariable String id) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<InsuranceCompany> ic = icService.findById(id);
            Optional<CollectionAccount> ca = caService.findByCompanyId(ic.get().getId());
            Double count = chService.getTotalhistorybyic(ca.get().getId());
            rs.setCode(200);
            rs.setObject(count);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("Error Occurred");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    @PostMapping(value="/collectionhistory/countbydates/ic/{id}")
    public ResponseEntity<Object> totalchsByDates(@PathVariable String id, @RequestBody Map<String, String> dates) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<InsuranceCompany> ic = icService.findById(id);
            Optional<CollectionAccount> ca = caService.findByCompanyId(ic.get().getId());
            Double count = chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(dates.get("start")),new SimpleDateFormat("yyyy-MM-dd").parse(dates.get("end")));
            rs.setCode(200);
            if(count == null)
                rs.setObject(0);
            else
            rs.setObject(count);
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("Error Occurred");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    @GetMapping(value="/collectionhistory/countbydates/ic/{id}/year/{year}")
    public ResponseEntity<Object> totalchsByYear(@PathVariable String id,@PathVariable String year) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<InsuranceCompany> ic = icService.findById(id);
            Optional<CollectionAccount> ca = caService.findByCompanyId(ic.get().getId());
            Map<String,Double> map = new HashMap<>();
            map.put("jan",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-01-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-01-31")));
            map.put("feb",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-02-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-02-28")));
            map.put("mar",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-03-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-03-31")));
            map.put("apr",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-04-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-04-30")));
            map.put("may",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-05-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-05-31")));
            map.put("may",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-05-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-05-31")));
            map.put("jun",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-06-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-06-30")));
            map.put("jul",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-07-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-07-31")));
            map.put("aug",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-08-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-08-31")));
            map.put("sep",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-09-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-09-30")));
            map.put("oct",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-10-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-10-31")));
            map.put("nov",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-11-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-11-30")));
            map.put("dec",chService.getTotalhistorybyicAnddates(ca.get().getId(), new SimpleDateFormat("yyyy-MM-dd").parse(year+"-12-1"),new SimpleDateFormat("yyyy-MM-dd").parse(year+"-12-31")));
            rs.setCode(200);
            rs.setDescription("Success");
            rs.setObject(map);
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
            Long canceled = sService.countAllByCompanyReferenceIdAndStatusAndDeletedStatus(companyId,SubscriptionStatus.CANCELED, false);
            Long paid = sService.countAllByCompanyReferenceIdAndStatusAndPaymentStatusAndDeletedStatus(companyId,SubscriptionStatus.APPROVED,true, false);
            Long all = sService.countAllByCompanyReferenceIdAndDeletedStatus(companyId, false);
            HashMap<String,Object> map  = new HashMap<>();
            map.put("pending", pending);
            map.put("approved", approve);
            map.put("rejected", rejected);
            map.put("canceled", canceled);
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