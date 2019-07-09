package com.vibs_backend.vibs.service;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.domain.Subscription;

public interface ISubscriptionService {
    public abstract Subscription create(Subscription s);
    public abstract Subscription update(Subscription s);
    public abstract Subscription delete(Subscription s);
    public abstract Optional<Subscription> findById(String id);
    public abstract List<Subscription> findAll();
    public abstract List<Subscription> findAllByCompany(String id);
    public abstract List<Subscription> findAllByVehicle(String id);
}