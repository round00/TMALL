package com.gjk.service;

import com.gjk.pojo.Category;
import com.gjk.pojo.Product;

import java.util.List;

public interface ProductService {
    /**
     *根据分类ID获取产品列表
     * */
    List<Product> getProductListByCid(Category category);
    List<Product> getProductListByCid(int cid);
    /**
     * 根据ID获取Product
     * */
    Product getProductByPid(int pid);
    /**
     * 填充product的所有image字段
     * */
    void setProductImages(Product p);
    /**
     * 填充product的first Image字段
     * */
    void setFirstProductImage(Product p);
    /**
     * 填充saleCount和reviewCount字段
     * */
    void setSaleAndReviewCount(Product p);
    /**
     * 根据name进行搜索
     * */
    List<Product> searchByName(String keyword);

    void update(Product product);
    void add(Product product);
    void delete(Product product);
}
