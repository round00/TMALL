package com.gjk.service;

import com.gjk.pojo.Property;

public interface PropertyService {
    /**
     * 根据属性id查找属性
     * */
    Property getPropertyById(int ptId);
}
