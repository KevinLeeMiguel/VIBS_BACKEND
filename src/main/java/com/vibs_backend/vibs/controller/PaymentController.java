package com.vibs_backend.vibs.controller;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.vibs_backend.vibs.dao.CollectionAccountDao;
import com.vibs_backend.vibs.dao.CollectionHistoryDao;
import com.vibs_backend.vibs.dao.PaymentTransactionDao;
import com.vibs_backend.vibs.domain.CollectionAccount;
import com.vibs_backend.vibs.domain.CollectionHistory;
import com.vibs_backend.vibs.domain.InsuranceCompany;
import com.vibs_backend.vibs.domain.PaymentTransaction;
import com.vibs_backend.vibs.domain.Subscription;
import com.vibs_backend.vibs.domain.SubscriptionStatus;
import com.vibs_backend.vibs.service.ISubscriptionService;
import com.vibs_backend.vibs.service.IinsuranceCompanyService;
import com.vibs_backend.vibs.utilities.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PaymentController {
    @Autowired
    private CollectionAccountDao caService;
    @Autowired
    private CollectionHistoryDao chService;
    @Autowired
    private PaymentTransactionDao pService;
    @Autowired
    private ISubscriptionService sService;
    @Autowired
    private IinsuranceCompanyService icService;

    @PutMapping(value = "/payments/save")
    public ResponseEntity<Object> createPayment(@RequestBody Map<String, Object> map, HttpServletRequest request) {
        ResponseBean rs = new ResponseBean();
        try {
            String username = request.getHeader("doneBy");
            String id = (String) map.get("subId");
            String tid = (String) map.get("tId");
            Optional<Subscription> s = sService.findById(id);
            if (s.isPresent()) {
                Subscription sub = s.get();
                if (sub.getStatus().equals(SubscriptionStatus.APPROVED) && sub.isPaymentStatus() == false) {
                    sub.setLastUpdatedBy(username);
                    sub.setLastUpdatedAt(new Date());
                    sub.setPaymentStatus(true);
                    sub.setQrCode((String) map.get("qr"));
                    sService.update(sub);
                    Optional<InsuranceCompany> ic = icService.findOne(s.get().getCompanyReferenceId());
                    if (ic.isPresent()) {
                        Optional<CollectionAccount> c = caService.findByCompanyId(ic.get().getId());
                        if (c.isPresent()) {
                            // update collection account
                            CollectionAccount ca = c.get();
                            ca.setBalance(ca.getBalance() + sub.getPrice());
                            ca.setLastUpdatedAt(new Date());
                            caService.save(ca);
                            // create collection history
                            CollectionHistory ch = new CollectionHistory();
                            ch.setAmount(sub.getPrice());
                            ch.setBalance(ca.getBalance());
                            ch.setTransactionReferenceId(tid);
                            ch.setCollectionAccount(ca);
                            chService.save(ch);
                            // create payment transaction
                            PaymentTransaction pt = new PaymentTransaction();
                            pt.setSubscription(sub);
                            pt.setAmount(sub.getPrice());
                            pt.setTransactionReferenceId(tid);
                            pService.save(pt);
                            rs.setCode(200);
                            rs.setDescription("success");
                        } else {
                            rs.setCode(404);
                            rs.setDescription("company seems to have no collection account");
                        }
                    } else {
                        rs.setCode(404);
                        rs.setDescription("Insurance Company not found");
                    }
                } else {
                    rs.setCode(400);
                    if (sub.getStatus().equals(SubscriptionStatus.APPROVED) && sub.isPaymentStatus() == true) {
                        rs.setDescription("subscription was paid already");
                    } else if (sub.getStatus().equals(SubscriptionStatus.CANCELED)) {
                        rs.setDescription("subscription already canceled");
                    } else {
                        rs.setDescription("subscription was rejected");
                    }
                }

            } else {
                rs.setCode(404);
                rs.setDescription("No subscription Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.setCode(300);
            rs.setDescription("error occured");
        }
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

}