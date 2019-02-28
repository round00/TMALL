package com.gjk.service;

import com.gjk.pojo.Category;
import com.gjk.pojo.Product;

import java.util.List;

public interface ProductService {
    /**
     *根据分类ID获取产品列表
     * */
    List<Product> getProductListByCid(Category category);
}
