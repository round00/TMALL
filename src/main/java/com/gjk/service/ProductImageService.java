package com.gjk.service;

import com.gjk.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {
    /**
     * 通过产品ID获取产品图片
     * */
    List<ProductImage> getProductImageByPid(int pid);
}
