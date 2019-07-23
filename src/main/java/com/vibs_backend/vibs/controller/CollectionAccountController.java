package com.vibs_backend.vibs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.vibs_backend.vibs.dao.CollectionAccountDao;
import com.vibs_backend.vibs.dao.CollectionHistoryDao;
import com.vibs_backend.vibs.domain.CollectionAccount;
import com.vibs_backend.vibs.domain.CollectionHistory;
import com.vibs_backend.vibs.utilities.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/collectionaccounts")
public class CollectionAccountController {
    @Autowired
    private CollectionAccountDao caService;
    @Autowired
    private CollectionHistoryDao chService;

    @GetMapping(value="/details/{id}")
    public ResponseEntity<Object> getDetails(@PathVariable String id) {
        ResponseBean rs = new ResponseBean();
        try {
            Optional<CollectionAccount> c = caService.findByCompanyId(id);
            if(c.isPresent()){
                List<CollectionHistory> li = chService.findByCollectionAccountId(c.get().getId());
                Map<String,Object> map = new HashMap<>();
                map.put("collectionAccount", c.get());
                map.put("history", li);
                rs.setCode(200);
                rs.setDescription("success");
                rs.setObject(map);
            }else{
                rs.setCode(404);
                rs.setDescription("collection account not found");
            }
        } catch (Exception e) {
            rs.setCode(300);
            rs.setDescription("error occures");
        }
        return new ResponseEntity<>(rs,HttpStatus.OK);
    }
    
}