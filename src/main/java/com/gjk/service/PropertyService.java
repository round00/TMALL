package com.gjk.service;

import com.gjk.pojo.Property;

import java.util.List;

public interface PropertyService {
    /**
     * 根据属性id查找属性
     * */
    Property getPropertyById(int ptId);
    /**
     * 根据分类ID查找属性列表
     * */
    List<Property> getPropertyList(int cid);

    void fillCategoryField(Property property);

    /**
     * 根据主键更新
     * */
    void update(Property property);

    int add(Property property);

    void delete(Property property);
}
