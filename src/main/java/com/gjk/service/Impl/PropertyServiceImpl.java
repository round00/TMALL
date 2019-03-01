package com.gjk.service.Impl;

import com.gjk.mapper.PropertyMapper;
import com.gjk.pojo.Property;
import com.gjk.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    PropertyMapper propertyMapper;

    @Override
    public Property getPropertyById(int ptId) {
        return propertyMapper.selectByPrimaryKey(ptId);
    }
}
