package com.gjk.service.Impl;

import com.gjk.mapper.PropertyMapper;
import com.gjk.pojo.Category;
import com.gjk.pojo.Property;
import com.gjk.pojo.PropertyExample;
import com.gjk.service.CategoryService;
import com.gjk.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    PropertyMapper propertyMapper;
    @Autowired
    CategoryService categoryService;
    @Override
    public Property getPropertyById(int ptId) {
        return propertyMapper.selectByPrimaryKey(ptId);
    }

    @Override
    public List<Property> getPropertyList(int cid) {
        PropertyExample propertyExample = new PropertyExample();
        propertyExample.createCriteria().andCidEqualTo(cid);
        return propertyMapper.selectByExample(propertyExample);
    }

    @Override
    public void fillCategoryField(Property property) {
        Category category = categoryService.getCategoryById(property.getCid());
        property.setCategory(category);
    }

    @Override
    public void update(Property property) {
        propertyMapper.updateByPrimaryKey(property);
    }

    @Override
    public int add(Property property) {
        propertyMapper.insertSelective(property);
        return property.getId();
    }

    @Override
    public void delete(Property property) {
        propertyMapper.deleteByPrimaryKey(property.getId());
    }
}
