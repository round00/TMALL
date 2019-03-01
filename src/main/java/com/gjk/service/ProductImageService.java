package com.gjk.service;

import com.gjk.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {

    String type_single = "type_single";
    String type_detail = "type_detail";

    /**
     * @Param1: 产品id
     * @Param2： 产品图片类型
     * */
    List<ProductImage> getProductImages(int pid, String type);
}
