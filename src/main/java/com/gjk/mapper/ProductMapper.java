package com.gjk.mapper;

import com.gjk.pojo.Product;

import java.util.List;

public interface ProductMapper {
    /**
     *根据分类ID获取产品列表
     * */
    List<Product> getProductListByCid(int cid);
}
