package com.vibs_backend.vibs.serviceimpl;

import com.vibs_backend.vibs.dao.DeletedItemDao;
import com.vibs_backend.vibs.domain.DeletedItem;
import com.vibs_backend.vibs.service.IDeletedItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletedItemServiceImpl implements IDeletedItemService {
    @Autowired
    private DeletedItemDao dao; 

    @Override
    public void create(DeletedItem item) {
         dao.save(item);
    }

    @Override
    public DeletedItem findOne(String id) {
        return dao.getOne(id);
    }
    
}