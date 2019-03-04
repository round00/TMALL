package com.gjk.service;

import com.gjk.pojo.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查新分类列表
     * */
    List<Category> getCategoryList();
    /**
     * 为每一个category填充product域
     * */
    void fillProductFields(Category category);
    /**
     * 为每一个category填充product域
     * */
    void fillProductFields(List<Category> categories);
    /**
     * 将每一个分类里的产品分成多行
     * */
    void fillProductFieldsByRow(List<Category> categories);

    /**
     * 根据ID查询分类
     * */
    Category getCategoryById(int cid);

    void add(Category category);
    void update(Category category);
    void delete(int cid);
}
