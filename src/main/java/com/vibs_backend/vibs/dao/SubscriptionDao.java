package com.vibs_backend.vibs.dao;

import java.util.List;
import java.util.Optional;

import com.vibs_backend.vibs.domain.Subscription;
import com.vibs_backend.vibs.domain.SubscriptionStatus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionDao extends JpaRepository<Subscription,String>{
    default Subscription deleted(Subscription entity) {
        entity.setDeletedStatus(true);
        return this.save(entity);
    }
    public Optional<Subscription> findByIdAndDeletedStatus(String id,Boolean status);
    public List<Subscription>findByDeletedStatus(Boolean deletedStatus);
    public List<Subscription>findByVehicleIdAndDeletedStatus(String vehicleId,Boolean deletedStatus);
    public List<Subscription>findByCompanyReferenceIdAndDeletedStatus(String referenceId,Boolean deletedStatus);
    public List<Subscription>findByCompanyReferenceIdAndStatusAndDeletedStatus(String referenceId,String status,Boolean deletedStatus);
	public List<Subscription> findByCompanyReferenceIdAndStatusAndDeletedStatus(String id, SubscriptionStatus status,boolean deletedStatus);
    public List<Subscription> findByDoneByAndStatusAndDeletedStatus(String doneBy, SubscriptionStatus status,boolean deletedStatus);
    //analytics
    Long countAllByCompanyReferenceIdAndStatusAndDeletedStatus(String id,SubscriptionStatus status,Boolean dStatus);
    Long countAllByCompanyReferenceIdAndStatusAndPaymentStatusAndDeletedStatus(String id,SubscriptionStatus status,Boolean pStatus,Boolean dStatus);
    Long countAllByCompanyReferenceIdAndDeletedStatus(String id,Boolean dStatus);

}