package com.gjk.service;

import com.gjk.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    /**
     *通过产品ID获得该产品的属性值
     * */
    List<PropertyValue> getPVSByProductId(int pid);
}
