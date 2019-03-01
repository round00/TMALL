package com.gjk.service.Impl;

import com.gjk.mapper.PropertyValueMapper;
import com.gjk.pojo.Property;
import com.gjk.pojo.PropertyValue;
import com.gjk.pojo.PropertyValueExample;
import com.gjk.service.PropertyService;
import com.gjk.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyService propertyService;

    @Override
    public List<PropertyValue> getPVSByProductId(int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(pid);

        List<PropertyValue> pvs = propertyValueMapper.selectByExample(propertyValueExample);
        for(PropertyValue p : pvs){
            Property property = propertyService.getPropertyById(p.getPtid());
            p.setProperty(property);
        }

        return pvs;
    }
}
