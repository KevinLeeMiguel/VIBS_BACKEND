package com.vibs_backend.vibs.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;


@Entity
public class DeletedItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id = UUID.randomUUID().toString();
    @Lob
    private byte[] item;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public byte[] getItem() {
        return item;
    }

    public void setItem(byte[] item) {
        this.item = item;
    }

   

  

}