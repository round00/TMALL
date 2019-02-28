package com.gjk.mapper;

import com.gjk.pojo.ProductImage;

import java.util.List;

public interface ProductImageMapper {
    List<ProductImage> getProductImageByPid(int pid);

}
