package com.gjk.service.Impl;

import com.gjk.mapper.ProductImageMapper;
import com.gjk.pojo.ProductImage;
import com.gjk.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageMapper productImageMapper;

    @Override
    public List<ProductImage> getProductImageByPid(int pid) {
        return productImageMapper.getProductImageByPid(pid);
    }
}
