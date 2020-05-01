package com.vibs_backend.vibs.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.vibs_backend.vibs.dao.SubscriptionContractDao;
import com.vibs_backend.vibs.dao.SubscriptionDao;
import com.vibs_backend.vibs.domain.AutoType;
import com.vibs_backend.vibs.domain.AutoUsage;
import com.vibs_backend.vibs.domain.InsuranceCompany;
import com.vibs_backend.vibs.domain.InsuranceType;
import com.vibs_backend.vibs.domain.Subscription;
import com.vibs_backend.vibs.domain.SubscriptionContract;
import com.vibs_backend.vibs.domain.SubscriptionStatus;
import com.vibs_backend.vibs.domain.Vehicle;
import com.vibs_backend.vibs.service.IAutoTypeService;
import com.vibs_backend.vibs.service.IAutoUsageService;
import com.vibs_backend.vibs.service.ISubscriptionService;
import com.vibs_backend.vibs.service.IVehicleService;
import com.vibs_backend.vibs.service.InsuranceCompanyService;
import com.vibs_backend.vibs.service.InsuranceTypesService;
import com.vibs_backend.vibs.utilities.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private ISubscriptionService sService;
    @Autowired
    private IVehicleService vService;
    @Autowired
    private InsuranceCompanyService icService;
    @Autowired
    private IAutoTypeService atypeService;
    @Autowired
    private IAutoUsageService aUsageService;
    @Autowired
    private InsuranceTypesService itypeService;
    @Autowired
    private SubscriptionContractDao subcaDao;
    @Autowired
    private SubscriptionDao sDao;

    @PostMapping(value = "/multiple/save")
    public ResponseEntity<Object> createMultiple(@RequestBody SubscriptionReqMultiple sq, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            Optional<Vehicle> v = vService.findById(sq.getVehicleId());
            if (v.isPresent()) {
                List<String> icsList = sq.getCompanyIds();
                for (String id : icsList) {
                    Optional<InsuranceCompany> ic = icService.findOne(id);
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
                                    s.setCompanyName(ic.get().getName());
                                    sService.create(s);
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
                }
                rs.setCode(200);
                rs.setDescription("insurance requests sent successfully");
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
                                s.setCompanyName(ic.get().getName());
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

    @GetMapping(value = "/vehicles/{plateNo}/all")
    public ResponseEntity<Object> getAllByVehicle(@PathVariable String plateNo, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            List<Subscription> li = sDao.findByVehiclePlateNoAndStatusAndPaymentStatusAndDeletedStatus(plateNo, SubscriptionStatus.APPROVED, true,false);
            Map<String,Object> map = new HashMap<>();
            List<Subscription> active = new ArrayList<>();
            List<Subscription> inactive = new ArrayList<>();
            for(Subscription s:li){
                if(new Date().before(s.getEndDate()) && new Date().after(s.getStartDate())){
                    active.add(s);
                }else{
                    inactive.add(s);
                }
            }
            map.put("active", active);
            map.put("inactive", inactive);
            rs.setCode(200);
            rs.setDescription("success");
            rs.setObject(map);
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
            if (st != null) {
                List<Subscription> li = sService.findAllByCompanyAndStatus(id, st);
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(li);
            } else {
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
            if (st != null) {
                List<Subscription> li = sService.findAllByUsernameAndStatus(username, st);
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(li);
            } else {
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

    @PutMapping(value = "/{id}/approve")
    public ResponseEntity<Object> approve(@PathVariable String id, @RequestBody approvalDetails ad,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<Subscription> sub = sService.findById(id);
            String username = request.getHeader("doneBy");
            if (sub.isPresent()) {
                Subscription subb = sub.get();
                if (subb.getStatus().equals(SubscriptionStatus.PENDING)) {
                    subb.setStatus(SubscriptionStatus.APPROVED);
                    subb.setStartDate(ad.getStartDate());
                    subb.setEndDate(ad.getEndDate());
                    subb.setPrice(ad.getAmount());
                    subb.setLastUpdatedBy(username);
                    subb.setLastUpdatedAt(new Date());
                    sService.update(subb);
                    rs.setCode(200);
                    rs.setDescription("subscription successfully approved");
                } else {
                    rs.setCode(400);
                    if (subb.getStatus().equals(SubscriptionStatus.APPROVED)) {
                        rs.setDescription("subscription already approved");
                    } else if (subb.getStatus().equals(SubscriptionStatus.CANCELED)) {
                        rs.setDescription("subscription was canceled");
                    } else {
                        rs.setDescription("subscription was rejected");
                    }
                }
            }
        } catch (Exception e) {
            rs.setCode(300);
            rs.setDescription("Internal error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/approvewithcontract")
    public ResponseEntity<Object> approveWithContract(@PathVariable String id, @RequestParam Map<String, String> ad,
            @RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<Subscription> sub = sService.findById(id);
            String username = request.getHeader("doneBy");
            if (sub.isPresent()) {
                Subscription subb = sub.get();
                if (subb.getStatus().equals(SubscriptionStatus.PENDING)) {
                    Object upRes = upLoad(file);
                    if(upRes != null){
                        subb.setStatus(SubscriptionStatus.APPROVED);
                        subb.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(ad.get("startDate")));
                        subb.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(ad.get("endDate")));
                        subb.setPrice(Double.parseDouble(ad.get("amount")));
                        subb.setLastUpdatedBy(username);
                        subb.setLastUpdatedAt(new Date());
                        sService.update(subb);
                        SubscriptionContract sc = new SubscriptionContract();
                        sc.setName(file.getOriginalFilename());
                        sc.setPath(upRes.toString());
                        sc.setSubscription(subb);
                        subcaDao.save(sc);
                        rs.setCode(200);
                        rs.setDescription("subscription successfully approved");
                        subb.setContract(sc);
                        rs.setObject(subb);
                    }else{
                        rs.setCode(500);
                        rs.setDescription("failed to upload the contract");
                    }
                } else {
                    rs.setCode(400);
                    if (subb.getStatus().equals(SubscriptionStatus.APPROVED)) {
                        rs.setDescription("subscription already approved");
                    } else if (subb.getStatus().equals(SubscriptionStatus.CANCELED)) {
                        rs.setDescription("subscription was canceled");
                    } else {
                        rs.setDescription("subscription was rejected");
                    }
                }
            }
        } catch (Exception e) {
            rs.setCode(300);
            rs.setDescription("Internal error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/reject")
    public ResponseEntity<Object> reject(@PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<Subscription> sub = sService.findById(id);
            String username = request.getHeader("doneBy");
            if (sub.isPresent()) {
                Subscription subb = sub.get();
                if (subb.getStatus().equals(SubscriptionStatus.PENDING)) {
                    subb.setStatus(SubscriptionStatus.REJECTED);
                    subb.setLastUpdatedBy(username);
                    subb.setLastUpdatedAt(new Date());
                    sService.update(subb);
                    rs.setCode(200);
                    rs.setDescription("subscription successfully rejected");
                    rs.setObject(subb);
                } else {
                    rs.setCode(400);
                    if (subb.getStatus().equals(SubscriptionStatus.APPROVED)) {
                        rs.setDescription("subscription was approved");
                    } else if (subb.getStatus().equals(SubscriptionStatus.CANCELED)) {
                        rs.setDescription("subscription was canceled");
                    } else {
                        rs.setDescription("subscription already rejected");
                    }
                }
            }
        } catch (Exception e) {
            rs.setCode(300);
            rs.setDescription("Internal error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<Object> cancel(@PathVariable String id, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<Subscription> sub = sService.findById(id);
            String username = request.getHeader("doneBy");
            if (sub.isPresent()) {
                Subscription subb = sub.get();
                if (subb.getStatus().equals(SubscriptionStatus.PENDING)) {
                    subb.setStatus(SubscriptionStatus.CANCELED);
                    subb.setLastUpdatedBy(username);
                    subb.setLastUpdatedAt(new Date());
                    sService.update(subb);
                    rs.setCode(200);
                    rs.setDescription("subscription successfully canceled");
                } else {
                    rs.setCode(400);
                    if (subb.getStatus().equals(SubscriptionStatus.APPROVED)) {
                        rs.setDescription("subscription was approved");
                    } else if (subb.getStatus().equals(SubscriptionStatus.CANCELED)) {
                        rs.setDescription("subscription already canceled");
                    } else {
                        rs.setDescription("subscription was rejected");
                    }
                }
            }
        } catch (Exception e) {
            rs.setCode(300);
            rs.setDescription("Internal error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/pay")
    public ResponseEntity<Object> pay(@PathVariable String id, @RequestBody Map<String, Object> map,
            HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<Subscription> sub = sService.findById(id);
            String username = request.getHeader("doneBy");
            if (sub.isPresent()) {
                Subscription subb = sub.get();
                if (subb.getStatus().equals(SubscriptionStatus.APPROVED) && subb.isPaymentStatus() == false) {
                    subb.setLastUpdatedBy(username);
                    subb.setLastUpdatedAt(new Date());
                    subb.setPaymentStatus(true);
                    subb.setQrCode((String) map.get("qr"));
                    sService.update(subb);
                    rs.setCode(200);
                    rs.setDescription("subscription successfully paid");
                } else {
                    rs.setCode(400);
                    if (subb.getStatus().equals(SubscriptionStatus.APPROVED) && subb.isPaymentStatus() == true) {
                        rs.setDescription("subscription was paid already");
                    } else if (subb.getStatus().equals(SubscriptionStatus.CANCELED)) {
                        rs.setDescription("subscription already canceled");
                    } else {
                        rs.setDescription("subscription was rejected");
                    }
                }
            }
        } catch (Exception e) {
            rs.setCode(300);
            rs.setDescription("Internal error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    
	@RequestMapping(path = "/documents/download/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(String param, @PathVariable("uuid") String uuid) throws IOException {

        SubscriptionContract doc = subcaDao.getOne(uuid);
		String filePath = doc.getPath();
		File file = new File(filePath);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		headers.add("Content-Disposition", "inline; filename=" + doc.getName());
		Path path = Paths.get(filePath);
		byte[] b = Files.readAllBytes((path));
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(bis));
	}

    public Object upLoad(MultipartFile file) {
        try {
            String parent = "VIBS_FILES";
            File f = new File(parent);
            String filename = file.getOriginalFilename();
            if (!f.exists())
                f.mkdir();
            File sub = new File(f, "Contracts");
            if (!sub.exists())
                sub.mkdir();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(sub.getPath() + "/" + filename);
            Files.write(path, bytes);
            return path;
        } catch (Exception e) {
            return null;
        }

    }

    public static class approvalDetails {
        private Date startDate;
        private Date endDate;
        private double amount;

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

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

    public static class SubscriptionReqMultiple {
        private Subscription sub;
        private String autoTypeId;
        private String autoUsageId;
        private String typeId;
        private String vehicleId;
        private List<String> companyIds;

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

        public List<String> getCompanyIds() {
            return companyIds;
        }

        public void setCompanyIds(List<String> companyIds) {
            this.companyIds = companyIds;
        }

    }

}