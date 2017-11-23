package com.example.pinan.otoutiao.model.bean;

import java.util.UUID;

/**
 * @author pinan
 * @date 2017/11/23
 */

public class PhotoBean {
    public String id = UUID.randomUUID().toString();
    public String name;
    public int size;
    
    public PhotoBean(String name, int size) {
        this.name = name;
        this.size = size;
    }
}
