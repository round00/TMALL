package com.gjk.service.Impl;

import com.gjk.mapper.PropertyValueMapper;
import com.gjk.pojo.Product;
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

    @Override
    public void add(PropertyValue propertyValue) {
        propertyValueMapper.insertSelective(propertyValue);
    }

    @Override
    public PropertyValue get(int id) {
        return propertyValueMapper.selectByPrimaryKey(id);
    }

    @Override
    public PropertyValue get(int pid, int ptid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);
        if(propertyValues.isEmpty()){
            return null;
        }
        return propertyValues.get(0);
    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    @Override
    public void init(Product product) {
        List<Property> properties = propertyService.getPropertyList(product.getCid());
        for (Property property : properties){
            PropertyValue pv = get(product.getId(), property.getId());
            if(pv == null){
                pv = new PropertyValue();
                pv.setPid(product.getId());
                pv.setPtid(property.getId());
                add(pv);
            }
        }
    }
}
