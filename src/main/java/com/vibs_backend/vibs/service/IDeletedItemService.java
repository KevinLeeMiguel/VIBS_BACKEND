package com.vibs_backend.vibs.service;

import com.vibs_backend.vibs.domain.DeletedItem;

public interface IDeletedItemService {
    public abstract void create(DeletedItem item);
    public abstract DeletedItem findOne(String id);

}