package com.gjk.service;

import com.gjk.pojo.Product;
import com.gjk.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {
    /**
     *通过产品ID获得该产品的属性值
     * */
    List<PropertyValue> getPVSByProductId(int pid);
    /**
     * 增加
     * */
    void add(PropertyValue propertyValue);
    /**
     * 通过id获取属性值
     * */
    PropertyValue get(int id);
    PropertyValue get(int pid, int ptid);
    /**
     * 更新
     * */
    void update(PropertyValue propertyValue);
    /**
     * 对产品的属性进行一次初始化。
     * 情景：分类新添加了属性，但是商品的属性值里还没有此属性
     * */
    void init(Product product);


}
