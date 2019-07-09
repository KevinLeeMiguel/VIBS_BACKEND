package com.vibs_backend.vibs.serviceimpl;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.dao.SubscriptionDao;
import com.vibs_backend.vibs.domain.Subscription;
import com.vibs_backend.vibs.service.ISubscriptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {
    @Autowired
    private SubscriptionDao dao;

    @Override
    public Subscription create(Subscription s) {
        return dao.save(s);
    }

    @Override
    public Subscription update(Subscription s) {
        return dao.save(s);
    }

    @Override
    public Subscription delete(Subscription s) {
       return dao.deleted(s);
    }

    @Override
    public Optional<Subscription> findById(String id) {
        return dao.findByIdAndDeletedStatus(id, false);
    }

    @Override
    public List<Subscription> findAll() {
        return dao.findByDeletedStatus(false);
    }

    @Override
    public List<Subscription> findAllByCompany(String id) {
        return dao.findByCompanyReferenceIdAndDeletedStatus(id, false);
    }

    @Override
    public List<Subscription> findAllByVehicle(String id) {
        return dao.findByVehicleIdAndDeletedStatus(id, false);
    }
}